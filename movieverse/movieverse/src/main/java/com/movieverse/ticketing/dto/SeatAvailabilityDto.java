package com.movieverse.ticketing.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class SeatAvailabilityDto {
    private String showtimeId;
    private String movieId;
    private String cinemaId;
    private String screenId;
    private LocalDateTime startTime;
    private int totalSeats;
    private int availableSeats;
    private int bookedSeats;
    private BigDecimal standardPrice;
    private BigDecimal premiumPrice;
    private boolean availabilityShared;
    private String availabilityMessage;
    private List<String> availableSeatNumbers;
    private Map<String, Boolean> fullSeatMap; // seatNumber -> isBooked
}
