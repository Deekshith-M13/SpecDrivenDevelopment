package com.movieverse.movie.controller;

import com.movieverse.common.response.ApiResponse;
import com.movieverse.movie.dto.MovieSummaryDto;
import com.movieverse.movie.model.Genre;
import com.movieverse.movie.model.Movie;
import com.movieverse.movie.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
@Tag(name = "Movie Catalogue", description = "Browse and search the movie database")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    @Operation(summary = "Get all movies", description = "Returns paginated list of all movies")
    public ResponseEntity<ApiResponse<List<MovieSummaryDto>>> getAllMovies() {
        return ResponseEntity.ok(ApiResponse.success(movieService.getSummaries()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get movie by ID", description = "Returns full movie details including cast and crew")
    public ResponseEntity<ApiResponse<Movie>> getMovieById(
            @Parameter(description = "Movie ID e.g. MOV-001") @PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(movieService.getMovieById(id)));
    }

    @GetMapping("/year/{year}")
    @Operation(summary = "Get movies by release year")
    public ResponseEntity<ApiResponse<List<Movie>>> getByYear(
            @Parameter(description = "4-digit year e.g. 2023") @PathVariable int year) {
        return ResponseEntity.ok(ApiResponse.success(movieService.getMoviesByYear(year)));
    }

    @GetMapping("/genre/{genre}")
    @Operation(summary = "Get movies by genre")
    public ResponseEntity<ApiResponse<List<Movie>>> getByGenre(
            @Parameter(description = "Genre enum value") @PathVariable Genre genre) {
        return ResponseEntity.ok(ApiResponse.success(movieService.getMoviesByGenre(genre)));
    }

    @GetMapping("/director")
    @Operation(summary = "Search movies by director name")
    public ResponseEntity<ApiResponse<List<Movie>>> getByDirector(
            @Parameter(description = "Director name (partial match)") @RequestParam String name) {
        return ResponseEntity.ok(ApiResponse.success(movieService.getMoviesByDirector(name)));
    }

    @GetMapping("/actor")
    @Operation(summary = "Search movies by actor name")
    public ResponseEntity<ApiResponse<List<Movie>>> getByActor(
            @Parameter(description = "Actor name (partial match)") @RequestParam String name) {
        return ResponseEntity.ok(ApiResponse.success(movieService.getMoviesByActor(name)));
    }
}
