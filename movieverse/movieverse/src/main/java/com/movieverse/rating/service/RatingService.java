package com.movieverse.rating.service;

import com.movieverse.common.exception.BusinessException;
import com.movieverse.common.exception.ResourceNotFoundException;
import com.movieverse.movie.dto.TopRatedMovieDto;
import com.movieverse.movie.model.Movie;
import com.movieverse.movie.service.MovieService;
import com.movieverse.rating.dto.RatingRequest;
import com.movieverse.rating.model.Rating;
import com.movieverse.rating.model.RatingSummary;
import com.movieverse.rating.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final MovieService movieService;

    // Real-world scores (IMDb / Rotten Tomatoes)
    private static final Map<String, double[]> RT_SCORES = Map.of(
        "MOV-001", new double[]{94, 94}, // [critics%, audience%]
        "MOV-002", new double[]{87, 91},
        "MOV-003", new double[]{99, 90},
        "MOV-004", new double[]{90, 90},
        "MOV-005", new double[]{91, 98},
        "MOV-006", new double[]{72, 86},
        "MOV-007", new double[]{97, 98},
        "MOV-008", new double[]{95, 88},
        "MOV-009", new double[]{93, 91},
        "MOV-010", new double[]{92, 96}
    );

    private static final Map<String, Double> IMDB_SCORES = Map.of(
        "MOV-001", 9.0, "MOV-002", 8.8, "MOV-003", 8.5,
        "MOV-004", 8.4, "MOV-005", 9.3, "MOV-006", 8.7,
        "MOV-007", 9.2, "MOV-008", 7.8, "MOV-009", 8.9,
        "MOV-010", 8.9
    );

    public Rating submitRating(String movieId, RatingRequest request) {
        movieService.validateMovieExists(movieId);

        List<Rating> existing = ratingRepository.findByMovieId(movieId);
        boolean alreadyRated = existing.stream()
                .anyMatch(r -> r.getUserId().equals(request.getUserId()));
        if (alreadyRated) {
            throw new BusinessException(
                "User " + request.getUserId() + " has already rated this movie",
                "DUPLICATE_RATING");
        }

        Rating rating = Rating.builder()
                .id(ratingRepository.nextId())
                .movieId(movieId)
                .userId(request.getUserId())
                .score(roundToHalf(request.getScore()))
                .review(request.getReview())
                .submittedAt(Instant.now())
                .verified(false)
                .build();

        return ratingRepository.save(rating);
    }

    public RatingSummary getSummary(String movieId) {
        var movie = movieService.getMovieById(movieId);
        List<Rating> ratings = ratingRepository.findByMovieId(movieId);

        double avg = ratings.stream()
                .mapToDouble(Rating::getScore)
                .average()
                .orElse(0.0);

        Map<Integer, Long> distribution = new TreeMap<>();
        for (int i = 1; i <= 10; i++) {
            final int bucket = i;
            distribution.put(bucket, ratings.stream()
                    .filter(r -> (int) Math.ceil(r.getScore()) == bucket)
                    .count());
        }

        double[] rt = RT_SCORES.getOrDefault(movieId, new double[]{0, 0});

        return RatingSummary.builder()
                .movieId(movieId)
                .movieTitle(movie.getTitle())
                .averageScore(Math.round(avg * 10.0) / 10.0)
                .totalRatings(ratings.size())
                .imdbScore(IMDB_SCORES.getOrDefault(movieId, 0.0))
                .rottenTomatoes(rt[0])
                .audienceScore(rt[1])
                .scoreDistribution(distribution)
                .build();
    }

    public TopRatedMovieDto getTopRatedMovie() {
        List<Movie> movies = movieService.getAllMovies();
        return movies.stream()
                .map(movie -> {
                    List<Rating> ratings = ratingRepository.findByMovieId(movie.getId());
                    if (ratings.isEmpty()) {
                        return null;
                    }
                    double average = ratings.stream()
                            .mapToDouble(Rating::getScore)
                            .average()
                            .orElse(0.0);
                    return TopRatedMovieDto.builder()
                            .id(movie.getId())
                            .title(movie.getTitle())
                            .year(movie.getYear())
                            .releaseDate(movie.getReleaseDate())
                            .genre(movie.getGenre())
                            .additionalGenres(movie.getAdditionalGenres())
                            .director(movie.getDirector())
                            .synopsis(movie.getSynopsis())
                            .runtimeMinutes(movie.getRuntimeMinutes())
                            .mpaaRating(movie.getMpaaRating())
                            .status(movie.getStatus())
                            .averageScore(Math.round(average * 10.0) / 10.0)
                            .totalRatings(ratings.size())
                            .build();
                })
                .filter(Objects::nonNull)
                .max((a, b) -> {
                    int scoreComparison = Double.compare(a.getAverageScore(), b.getAverageScore());
                    if (scoreComparison != 0) {
                        return scoreComparison;
                    }
                    int countComparison = Integer.compare(a.getTotalRatings(), b.getTotalRatings());
                    if (countComparison != 0) {
                        return countComparison;
                    }
                    return b.getId().compareTo(a.getId());
                })
                .orElseThrow(() -> new ResourceNotFoundException("TopRatedMovie", "top-rated"));
    }

    public List<Rating> getRatings(String movieId) {
        movieService.validateMovieExists(movieId);
        return ratingRepository.findByMovieId(movieId);
    }

    private double roundToHalf(double score) {
        return Math.round(score * 2.0) / 2.0;
    }
}
