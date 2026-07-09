package com.movieverse.ticketing.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Showtime {
    private String id;
    private String movieId;
    private String cinemaId;
    private String screenId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int totalSeats;
    private int availableSeats;
    private BigDecimal standardPrice;
    private BigDecimal premiumPrice;   // VIP / front rows
    private String language;           // "English", "Dubbed"
    private boolean hasSubtitles;
    private ShowtimeStatus status;
}
