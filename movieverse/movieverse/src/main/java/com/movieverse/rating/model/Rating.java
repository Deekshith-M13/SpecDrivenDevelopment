package com.movieverse.rating.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class Rating {
    private String id;
    private String movieId;
    private String userId;
    private double score;           // 0.5 - 10.0 in 0.5 increments
    private String review;
    private Instant submittedAt;
    private boolean verified;       // verified purchase / watch
}
