package com.movieverse.rating;

import com.movieverse.common.exception.BusinessException;
import com.movieverse.movie.dto.TopRatedMovieDto;
import com.movieverse.movie.model.Genre;
import com.movieverse.movie.model.Movie;
import com.movieverse.movie.service.MovieService;
import com.movieverse.rating.dto.RatingRequest;
import com.movieverse.rating.model.Rating;
import com.movieverse.rating.model.RatingSummary;
import com.movieverse.rating.repository.RatingRepository;
import com.movieverse.rating.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RatingService Unit Tests")
class RatingServiceTest {

    @Mock RatingRepository ratingRepository;
    @Mock MovieService movieService;
    @InjectMocks RatingService ratingService;

    private Movie movie;
    private RatingRequest request;

    @BeforeEach
    void setUp() {
        movie = Movie.builder()
                .id("MOV-001").title("The Dark Knight").year(2008)
                .genre(Genre.ACTION).additionalGenres(Collections.emptyList())
                .cast(Collections.emptyList()).crew(Collections.emptyList())
                .writers(Collections.emptyList()).producers(Collections.emptyList())
                .build();

        request = new RatingRequest();
        request.setUserId("user-123");
        request.setScore(9.0);
        request.setReview("Absolute masterpiece.");
    }

    @Test
    @DisplayName("submitRating creates and returns a new rating")
    void submitRating_success() {
        when(ratingRepository.findByMovieId("MOV-001")).thenReturn(Collections.emptyList());
        when(ratingRepository.nextId()).thenReturn("RTG-999");
        Rating saved = Rating.builder().id("RTG-999").movieId("MOV-001")
                .userId("user-123").score(9.0).submittedAt(Instant.now()).build();
        when(ratingRepository.save(any())).thenReturn(saved);

        Rating result = ratingService.submitRating("MOV-001", request);

        assertThat(result.getId()).isEqualTo("RTG-999");
        assertThat(result.getScore()).isEqualTo(9.0);
        verify(ratingRepository).save(any(Rating.class));
    }

    @Test
    @DisplayName("submitRating throws BusinessException for duplicate rating")
    void submitRating_duplicateRating() {
        Rating existing = Rating.builder().id("RTG-1").movieId("MOV-001")
                .userId("user-123").score(8.0).submittedAt(Instant.now()).build();
        when(ratingRepository.findByMovieId("MOV-001")).thenReturn(List.of(existing));

        assertThatThrownBy(() -> ratingService.submitRating("MOV-001", request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("already rated");
        verify(ratingRepository, never()).save(any());
    }

    @Test
    @DisplayName("getSummary computes average correctly")
    void getSummary_computesAverage() {
        when(movieService.getMovieById("MOV-001")).thenReturn(movie);
        List<Rating> ratings = List.of(
            Rating.builder().id("R1").movieId("MOV-001").userId("u1").score(9.0).submittedAt(Instant.now()).build(),
            Rating.builder().id("R2").movieId("MOV-001").userId("u2").score(8.0).submittedAt(Instant.now()).build(),
            Rating.builder().id("R3").movieId("MOV-001").userId("u3").score(10.0).submittedAt(Instant.now()).build()
        );
        when(ratingRepository.findByMovieId("MOV-001")).thenReturn(ratings);

        RatingSummary summary = ratingService.getSummary("MOV-001");

        assertThat(summary.getAverageScore()).isEqualTo(9.0);
        assertThat(summary.getTotalRatings()).isEqualTo(3);
        assertThat(summary.getImdbScore()).isEqualTo(9.0);  // seeded value
    }

    @Test
    @DisplayName("getSummary returns zero average for no ratings")
    void getSummary_noRatings() {
        when(movieService.getMovieById("MOV-001")).thenReturn(movie);
        when(ratingRepository.findByMovieId("MOV-001")).thenReturn(Collections.emptyList());

        RatingSummary summary = ratingService.getSummary("MOV-001");

        assertThat(summary.getAverageScore()).isEqualTo(0.0);
        assertThat(summary.getTotalRatings()).isZero();
    }

    @Test
    @DisplayName("getTopRatedMovie selects the highest average rating")
    void getTopRatedMovie_selectsHighestAverage() {
        Movie movie2 = Movie.builder().id("MOV-002").title("Inception").year(2010)
                .genre(Genre.SCI_FI).additionalGenres(Collections.emptyList())
                .cast(Collections.emptyList()).crew(Collections.emptyList())
                .writers(Collections.emptyList()).producers(Collections.emptyList())
                .build();

        when(movieService.getAllMovies()).thenReturn(List.of(movie, movie2));
        when(ratingRepository.findByMovieId("MOV-001")).thenReturn(List.of(
                Rating.builder().id("R1").movieId("MOV-001").userId("u1").score(9.0).submittedAt(Instant.now()).build(),
                Rating.builder().id("R2").movieId("MOV-001").userId("u2").score(8.0).submittedAt(Instant.now()).build()
        ));
        when(ratingRepository.findByMovieId("MOV-002")).thenReturn(List.of(
                Rating.builder().id("R3").movieId("MOV-002").userId("u3").score(9.5).submittedAt(Instant.now()).build()
        ));

        TopRatedMovieDto topRated = ratingService.getTopRatedMovie();

        assertThat(topRated.getId()).isEqualTo("MOV-002");
        assertThat(topRated.getAverageScore()).isEqualTo(9.5);
        assertThat(topRated.getTotalRatings()).isEqualTo(1);
    }

    @Test
    @DisplayName("getTopRatedMovie applies tie-breaking rules")
    void getTopRatedMovie_tieBreaksByRatingCountAndId() {
        Movie movie2 = Movie.builder().id("MOV-002").title("Inception").year(2010)
                .genre(Genre.SCI_FI).additionalGenres(Collections.emptyList())
                .cast(Collections.emptyList()).crew(Collections.emptyList())
                .writers(Collections.emptyList()).producers(Collections.emptyList())
                .build();

        when(movieService.getAllMovies()).thenReturn(List.of(movie, movie2));
        when(ratingRepository.findByMovieId("MOV-001")).thenReturn(List.of(
                Rating.builder().id("R1").movieId("MOV-001").userId("u1").score(9.0).submittedAt(Instant.now()).build(),
                Rating.builder().id("R2").movieId("MOV-001").userId("u2").score(9.0).submittedAt(Instant.now()).build()
        ));
        when(ratingRepository.findByMovieId("MOV-002")).thenReturn(List.of(
                Rating.builder().id("R3").movieId("MOV-002").userId("u3").score(9.0).submittedAt(Instant.now()).build()
        ));

        TopRatedMovieDto topRated = ratingService.getTopRatedMovie();

        assertThat(topRated.getId()).isEqualTo("MOV-001");
    }

    @Test
    @DisplayName("getTopRatedMovie throws when no rated movies exist")
    void getTopRatedMovie_noRatings() {
        when(movieService.getAllMovies()).thenReturn(List.of(movie));
        when(ratingRepository.findByMovieId("MOV-001")).thenReturn(Collections.emptyList());

        assertThatThrownBy(ratingService::getTopRatedMovie)
                .isInstanceOf(RuntimeException.class);
    }
}
