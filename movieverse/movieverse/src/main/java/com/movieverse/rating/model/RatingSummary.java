package com.movieverse.rating.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class RatingSummary {
    private String movieId;
    private String movieTitle;
    private double averageScore;
    private int totalRatings;
    private double imdbScore;       // seeded real-world score
    private double rottenTomatoes;  // critic score %
    private double audienceScore;   // audience score %
    private Map<Integer, Long> scoreDistribution; // score bucket -> count
}
