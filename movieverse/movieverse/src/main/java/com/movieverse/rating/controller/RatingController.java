package com.movieverse.rating.controller;

import com.movieverse.common.response.ApiResponse;
import com.movieverse.movie.dto.TopRatedMovieDto;
import com.movieverse.rating.dto.RatingRequest;
import com.movieverse.rating.model.Rating;
import com.movieverse.rating.model.RatingSummary;
import com.movieverse.rating.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
@Tag(name = "Rating Service", description = "Submit and view movie ratings")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/{movieId}")
    @Operation(summary = "Submit a rating for a movie")
    public ResponseEntity<ApiResponse<Rating>> submitRating(
            @PathVariable String movieId,
            @Valid @RequestBody RatingRequest request) {
        Rating rating = ratingService.submitRating(movieId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(rating, "Rating submitted successfully"));
    }

    @GetMapping("/{movieId}/summary")
    @Operation(summary = "Get aggregated rating summary with IMDb and RT scores")
    public ResponseEntity<ApiResponse<RatingSummary>> getSummary(@PathVariable String movieId) {
        return ResponseEntity.ok(ApiResponse.success(ratingService.getSummary(movieId)));
    }

    @GetMapping("/{movieId}/all")
    @Operation(summary = "Get all individual ratings for a movie")
    public ResponseEntity<ApiResponse<List<Rating>>> getAllRatings(@PathVariable String movieId) {
        return ResponseEntity.ok(ApiResponse.success(ratingService.getRatings(movieId)));
    }

    @GetMapping("/top-rated")
    @Operation(summary = "Get the top-rated movie by average user rating")
    public ResponseEntity<ApiResponse<TopRatedMovieDto>> getTopRatedMovie() {
        return ResponseEntity.ok(ApiResponse.success(ratingService.getTopRatedMovie()));
    }
}
