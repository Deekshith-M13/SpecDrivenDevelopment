package com.movieverse.ticketing.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Builder
public class Booking {
    private String id;                   // Booking reference e.g. BKG-ABCD1234
    private String showtimeId;
    private String movieId;
    private String cinemaId;
    private String userId;
    private List<String> seatNumbers;    // e.g. ["D4", "D5"]
    private int numberOfTickets;
    private BigDecimal unitPrice;
    private BigDecimal totalAmount;
    private String ticketType;           // STANDARD / PREMIUM
    private BookingStatus status;
    private Instant bookedAt;
    private Instant expiresAt;           // 15 min hold window
    private String paymentReference;
    private String confirmationCode;
    private int loyaltyPointsEarned;  // NEW FIELD
}
