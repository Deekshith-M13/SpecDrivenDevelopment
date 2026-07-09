package com.movieverse.rating.repository;

import com.movieverse.rating.model.Rating;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class RatingRepository {

    private final Map<String, List<Rating>> byMovie = new ConcurrentHashMap<>();
    private final AtomicLong idSeq = new AtomicLong(100);

    @PostConstruct
    public void seed() {
        // Seed realistic rating distributions based on IMDb scores
        seedMovieRatings("MOV-001", 9.0, 50); // Dark Knight 9.0
        seedMovieRatings("MOV-002", 8.8, 45); // Inception 8.8
        seedMovieRatings("MOV-003", 8.5, 40); // Parasite 8.5
        seedMovieRatings("MOV-004", 8.4, 48); // Endgame 8.4
        seedMovieRatings("MOV-005", 9.3, 55); // Shawshank 9.3
        seedMovieRatings("MOV-006", 8.7, 42); // Interstellar 8.7
        seedMovieRatings("MOV-007", 9.2, 52); // Godfather 9.2
        seedMovieRatings("MOV-008", 7.8, 35); // EEAAO 7.8
        seedMovieRatings("MOV-009", 8.9, 46); // Oppenheimer 8.9
        seedMovieRatings("MOV-010", 8.9, 50); // Pulp Fiction 8.9
        log.info("RatingRepository seeded");
    }

    private void seedMovieRatings(String movieId, double targetAvg, int count) {
        List<Rating> ratings = new ArrayList<>();
        Random rng = new Random(movieId.hashCode());
        for (int i = 0; i < count; i++) {
            double score = Math.min(10.0, Math.max(0.5,
                    targetAvg + (rng.nextGaussian() * 0.8)));
            // Round to nearest 0.5
            score = Math.round(score * 2.0) / 2.0;
            ratings.add(Rating.builder()
                    .id("RTG-" + idSeq.getAndIncrement())
                    .movieId(movieId)
                    .userId("user-seed-" + i)
                    .score(score)
                    .review(generateReview(score, rng))
                    .submittedAt(Instant.now().minus(rng.nextInt(365), ChronoUnit.DAYS))
                    .verified(rng.nextBoolean())
                    .build());
        }
        byMovie.put(movieId, new ArrayList<>(ratings));
    }

    private String generateReview(double score, Random rng) {
        String[] positive = {
            "An absolute masterpiece.", "One of the best films ever made.",
            "Stunning cinematography and performances.", "A timeless classic.",
            "Completely changed how I see cinema."
        };
        String[] neutral = {
            "Good film, some slow parts.", "Worth watching once.",
            "Solid performances, average story.", "Entertaining overall."
        };
        String[] negative = {
            "Overhyped, disappointed.", "Not my taste.",
            "Too long and slow.", "Expected more."
        };
        if (score >= 8) return positive[rng.nextInt(positive.length)];
        if (score >= 6) return neutral[rng.nextInt(neutral.length)];
        return negative[rng.nextInt(negative.length)];
    }

    public List<Rating> findByMovieId(String movieId) {
        return byMovie.getOrDefault(movieId, Collections.emptyList());
    }

    public Rating save(Rating rating) {
        byMovie.computeIfAbsent(rating.getMovieId(), k -> new ArrayList<>()).add(rating);
        return rating;
    }

    public String nextId() {
        return "RTG-" + idSeq.getAndIncrement();
    }
}
