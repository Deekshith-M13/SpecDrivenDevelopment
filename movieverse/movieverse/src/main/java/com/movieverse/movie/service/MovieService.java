package com.movieverse.movie.service;

import com.movieverse.common.exception.ResourceNotFoundException;
import com.movieverse.movie.dto.MovieSummaryDto;
import com.movieverse.movie.model.Genre;
import com.movieverse.movie.model.Movie;
import com.movieverse.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(String id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", id));
    }

    public List<Movie> getMoviesByYear(int year) {
        log.debug("Fetching movies for year: {}", year);
        return movieRepository.findByYear(year);
    }

    public List<Movie> getMoviesByGenre(Genre genre) {
        log.debug("Fetching movies for genre: {}", genre);
        return movieRepository.findByGenre(genre);
    }

    public List<Movie> getMoviesByDirector(String director) {
        return movieRepository.findByDirector(director);
    }

    public List<Movie> getMoviesByActor(String actorName) {
        return movieRepository.findByActor(actorName);
    }

    public List<MovieSummaryDto> getSummaries() {
        return movieRepository.findAll().stream()
                .map(this::toSummary)
                .collect(Collectors.toList());
    }

    public void validateMovieExists(String movieId) {
        if (!movieRepository.exists(movieId)) {
            throw new ResourceNotFoundException("Movie", movieId);
        }
    }

    private MovieSummaryDto toSummary(Movie m) {
        return MovieSummaryDto.builder()
                .id(m.getId())
                .title(m.getTitle())
                .year(m.getYear())
                .releaseDate(m.getReleaseDate())
                .genre(m.getGenre())
                .additionalGenres(m.getAdditionalGenres())
                .director(m.getDirector())
                .synopsis(m.getSynopsis())
                .runtimeMinutes(m.getRuntimeMinutes())
                .mpaaRating(m.getMpaaRating())
                .status(m.getStatus())
                .build();
    }
}
