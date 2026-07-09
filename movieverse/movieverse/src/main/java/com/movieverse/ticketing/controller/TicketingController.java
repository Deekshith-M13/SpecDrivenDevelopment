package com.movieverse.ticketing.controller;

import com.movieverse.common.response.ApiResponse;
import com.movieverse.ticketing.dto.BookingRequest;
import com.movieverse.ticketing.dto.SeatAvailabilityDto;
import com.movieverse.ticketing.model.Booking;
import com.movieverse.ticketing.model.Cinema;
import com.movieverse.ticketing.model.Showtime;
import com.movieverse.ticketing.service.TicketingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticketing")
@RequiredArgsConstructor
@Tag(name = "Ticketing Service", description = "Cinema hall booking — availability, purchase, and cancellation")
public class TicketingController {

    private final TicketingService ticketingService;

    // ── Cinemas ───────────────────────────────────────────────────────────────

    @GetMapping("/cinemas")
    @Operation(summary = "List all cinemas with screens")
    public ResponseEntity<ApiResponse<List<Cinema>>> getCinemas() {
        return ResponseEntity.ok(ApiResponse.success(ticketingService.getAllCinemas()));
    }

    @GetMapping("/cinemas/{cinemaId}")
    @Operation(summary = "Get cinema detail with screens")
    public ResponseEntity<ApiResponse<Cinema>> getCinema(@PathVariable String cinemaId) {
        return ResponseEntity.ok(ApiResponse.success(ticketingService.getCinema(cinemaId)));
    }

    // ── Showtimes ─────────────────────────────────────────────────────────────

    @GetMapping("/movies/{movieId}/showtimes")
    @Operation(summary = "Get all showtimes for a movie across all cinemas")
    public ResponseEntity<ApiResponse<List<Showtime>>> getShowtimesForMovie(
            @PathVariable String movieId) {
        return ResponseEntity.ok(ApiResponse.success(ticketingService.getShowtimesForMovie(movieId)));
    }

    @GetMapping("/cinemas/{cinemaId}/showtimes")
    @Operation(summary = "Get all showtimes at a specific cinema")
    public ResponseEntity<ApiResponse<List<Showtime>>> getShowtimesForCinema(
            @PathVariable String cinemaId) {
        return ResponseEntity.ok(ApiResponse.success(ticketingService.getShowtimesForCinema(cinemaId)));
    }

    @GetMapping("/showtimes/{showtimeId}")
    @Operation(summary = "Get showtime detail")
    public ResponseEntity<ApiResponse<Showtime>> getShowtime(@PathVariable String showtimeId) {
        return ResponseEntity.ok(ApiResponse.success(ticketingService.getShowtime(showtimeId)));
    }

    // ── Availability ──────────────────────────────────────────────────────────

    @GetMapping("/showtimes/{showtimeId}/availability")
    @Operation(summary = "Get full seat availability map for a showtime",
               description = "Returns which seats are available (false) or booked (true)")
    public ResponseEntity<ApiResponse<SeatAvailabilityDto>> getAvailability(
            @PathVariable String showtimeId) {
        return ResponseEntity.ok(ApiResponse.success(ticketingService.getAvailability(showtimeId)));
    }

    // ── Booking ───────────────────────────────────────────────────────────────

    @PostMapping("/bookings")
    @Operation(summary = "Book tickets — ecommerce transactional endpoint",
               description = """
               Books one or more seats for a showtime.
               
               Business rules:
               - Maximum 8 tickets per transaction
               - All requested seat numbers must be available
               - Showtime must have status ON_SALE
               - paymentReference simulates upstream payment gateway confirmation
               """)
    public ResponseEntity<ApiResponse<Booking>> bookTickets(@Valid @RequestBody BookingRequest request) {
        Booking booking = ticketingService.bookTickets(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(booking, "Booking confirmed! Reference: " + booking.getConfirmationCode()));
    }

    @GetMapping("/bookings/{bookingId}")
    @Operation(summary = "Get booking by ID")
    public ResponseEntity<ApiResponse<Booking>> getBooking(@PathVariable String bookingId) {
        return ResponseEntity.ok(ApiResponse.success(ticketingService.getBooking(bookingId)));
    }

    @GetMapping("/bookings/user/{userId}")
    @Operation(summary = "Get all bookings for a user")
    public ResponseEntity<ApiResponse<List<Booking>>> getUserBookings(@PathVariable String userId) {
        return ResponseEntity.ok(ApiResponse.success(ticketingService.getBookingsForUser(userId)));
    }

    @DeleteMapping("/bookings/{bookingId}")
    @Operation(summary = "Cancel a booking and release seats back to inventory")
    public ResponseEntity<ApiResponse<Booking>> cancelBooking(@PathVariable String bookingId) {
        return ResponseEntity.ok(ApiResponse.success(
                ticketingService.cancelBooking(bookingId), "Booking cancelled successfully"));
    }
}
