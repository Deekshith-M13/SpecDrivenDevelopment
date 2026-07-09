package com.movieverse.movie;

import com.movieverse.common.exception.ResourceNotFoundException;
import com.movieverse.movie.model.Genre;
import com.movieverse.movie.model.Movie;
import com.movieverse.movie.repository.MovieRepository;
import com.movieverse.movie.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MovieService Unit Tests")
class MovieServiceTest {

    @Mock MovieRepository movieRepository;
    @InjectMocks MovieService movieService;

    private Movie darkKnight;

    @BeforeEach
    void setUp() {
        darkKnight = Movie.builder()
                .id("MOV-001").title("The Dark Knight").year(2008)
                .releaseDate(LocalDate.of(2008, 7, 18))
                .genre(Genre.ACTION)
                .additionalGenres(List.of(Genre.CRIME, Genre.DRAMA))
                .director("Christopher Nolan")
                .cast(Collections.emptyList())
                .crew(Collections.emptyList())
                .writers(Collections.emptyList())
                .producers(Collections.emptyList())
                .build();
    }

    @Test
    @DisplayName("getMovieById returns movie when found")
    void getMovieById_found() {
        when(movieRepository.findById("MOV-001")).thenReturn(Optional.of(darkKnight));
        Movie result = movieService.getMovieById("MOV-001");
        assertThat(result.getTitle()).isEqualTo("The Dark Knight");
        assertThat(result.getYear()).isEqualTo(2008);
    }

    @Test
    @DisplayName("getMovieById throws ResourceNotFoundException when not found")
    void getMovieById_notFound() {
        when(movieRepository.findById("INVALID")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> movieService.getMovieById("INVALID"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("INVALID");
    }

    @Test
    @DisplayName("getMoviesByYear returns filtered results")
    void getMoviesByYear() {
        when(movieRepository.findByYear(2008)).thenReturn(List.of(darkKnight));
        List<Movie> movies = movieService.getMoviesByYear(2008);
        assertThat(movies).hasSize(1);
        assertThat(movies.get(0).getYear()).isEqualTo(2008);
    }

    @Test
    @DisplayName("getMoviesByYear returns empty list for year with no movies")
    void getMoviesByYear_empty() {
        when(movieRepository.findByYear(1800)).thenReturn(Collections.emptyList());
        assertThat(movieService.getMoviesByYear(1800)).isEmpty();
    }

    @Test
    @DisplayName("getMoviesByGenre returns ACTION movies")
    void getMoviesByGenre() {
        when(movieRepository.findByGenre(Genre.ACTION)).thenReturn(List.of(darkKnight));
        List<Movie> result = movieService.getMoviesByGenre(Genre.ACTION);
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getGenre()).isEqualTo(Genre.ACTION);
    }

    @Test
    @DisplayName("validateMovieExists does not throw for valid id")
    void validateMovieExists_valid() {
        when(movieRepository.exists("MOV-001")).thenReturn(true);
        assertThatNoException().isThrownBy(() -> movieService.validateMovieExists("MOV-001"));
    }

    @Test
    @DisplayName("validateMovieExists throws for invalid id")
    void validateMovieExists_invalid() {
        when(movieRepository.exists("INVALID")).thenReturn(false);
        assertThatThrownBy(() -> movieService.validateMovieExists("INVALID"))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
