package com.movieverse.ticketing.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class BookingRequest {

    @NotBlank(message = "showtimeId is required")
    private String showtimeId;

    @NotBlank(message = "userId is required")
    private String userId;

    @NotNull @Min(value = 1, message = "Must book at least 1 ticket")
    @Max(value = 8, message = "Cannot book more than 8 tickets at once")
    private Integer numberOfTickets;

    @NotEmpty(message = "Seat selection is required")
    private List<String> requestedSeats;

    @NotBlank
    @Pattern(regexp = "STANDARD|PREMIUM", message = "ticketType must be STANDARD or PREMIUM")
    private String ticketType;

    @NotBlank(message = "paymentReference is required")
    private String paymentReference;
}
