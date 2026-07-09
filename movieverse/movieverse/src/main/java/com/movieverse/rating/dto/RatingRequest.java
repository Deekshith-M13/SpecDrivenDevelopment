package com.movieverse.rating.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RatingRequest {

    @NotBlank(message = "userId is required")
    private String userId;

    @NotNull(message = "score is required")
    @DecimalMin(value = "0.5", message = "Score must be at least 0.5")
    @DecimalMax(value = "10.0", message = "Score cannot exceed 10.0")
    private Double score;

    @Size(max = 2000, message = "Review cannot exceed 2000 characters")
    private String review;
}
