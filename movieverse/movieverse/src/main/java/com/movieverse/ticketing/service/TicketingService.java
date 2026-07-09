package com.movieverse.ticketing.service;

import com.movieverse.common.exception.BusinessException;
import com.movieverse.common.exception.ResourceNotFoundException;
import com.movieverse.movie.service.MovieService;
import com.movieverse.ticketing.dto.BookingRequest;
import com.movieverse.ticketing.dto.SeatAvailabilityDto;
import com.movieverse.ticketing.model.*;
import com.movieverse.ticketing.repository.TicketingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketingService {

    private static final LocalTime AVAILABILITY_CUTOFF_TIME = LocalTime.of(22, 0);

    private final TicketingRepository ticketingRepository;
    private final MovieService movieService;

    // ── Cinemas ───────────────────────────────────────────────────────────────

    public List<Cinema> getAllCinemas() {
        return ticketingRepository.findAllCinemas();
    }

    public Cinema getCinema(String cinemaId) {
        return ticketingRepository.findCinemaById(cinemaId)
                .orElseThrow(() -> new ResourceNotFoundException("Cinema", cinemaId));
    }

    // ── Showtimes ─────────────────────────────────────────────────────────────

    public List<Showtime> getShowtimesForMovie(String movieId) {
        movieService.validateMovieExists(movieId);
        return ticketingRepository.findShowtimesByMovie(movieId);
    }

    public List<Showtime> getShowtimesForCinema(String cinemaId) {
        getCinema(cinemaId); // validates existence
        return ticketingRepository.findShowtimesByCinema(cinemaId);
    }

    public Showtime getShowtime(String showtimeId) {
        return ticketingRepository.findShowtimeById(showtimeId)
                .orElseThrow(() -> new ResourceNotFoundException("Showtime", showtimeId));
    }

    // ── Seat availability ─────────────────────────────────────────────────────

    public SeatAvailabilityDto getAvailability(String showtimeId) {
        Showtime st = getShowtime(showtimeId);
        boolean isAfterCutoff = st.getStartTime().toLocalTime().isAfter(AVAILABILITY_CUTOFF_TIME);

        if (isAfterCutoff) {
            return SeatAvailabilityDto.builder()
                    .showtimeId(showtimeId)
                    .movieId(st.getMovieId())
                    .cinemaId(st.getCinemaId())
                    .screenId(st.getScreenId())
                    .startTime(st.getStartTime())
                    .totalSeats(st.getTotalSeats())
                    .standardPrice(st.getStandardPrice())
                    .premiumPrice(st.getPremiumPrice())
                    .availabilityShared(false)
                    .availabilityMessage("Seat availability is hidden for shows starting after 10:00 PM")
                    .availableSeatNumbers(List.of())
                    .fullSeatMap(Map.of())
                    .build();
        }

        List<String> available = ticketingRepository.getAvailableSeats(showtimeId);
        Map<String, Boolean> fullMap = ticketingRepository.getSeatMap(showtimeId);

        return SeatAvailabilityDto.builder()
                .showtimeId(showtimeId)
                .movieId(st.getMovieId())
                .cinemaId(st.getCinemaId())
                .screenId(st.getScreenId())
                .startTime(st.getStartTime())
                .totalSeats(st.getTotalSeats())
                .availableSeats(st.getAvailableSeats())
                .bookedSeats(st.getTotalSeats() - st.getAvailableSeats())
                .standardPrice(st.getStandardPrice())
                .premiumPrice(st.getPremiumPrice())
                .availabilityShared(true)
                .availabilityMessage("Seat availability shared")
                .availableSeatNumbers(available)
                .fullSeatMap(fullMap)
                .build();
    }

    // ── Booking ───────────────────────────────────────────────────────────────

    public Booking bookTickets(BookingRequest request) {
        Showtime st = getShowtime(request.getShowtimeId());

        // Business rule: cannot book cancelled or completed shows
        if (st.getStatus() == ShowtimeStatus.CANCELLED || st.getStatus() == ShowtimeStatus.COMPLETED) {
            throw new BusinessException(
                "Showtime " + request.getShowtimeId() + " is not available for booking",
                "SHOWTIME_UNAVAILABLE");
        }

        // Business rule: check sufficient availability
        if (st.getAvailableSeats() < request.getNumberOfTickets()) {
            throw new BusinessException(
                "Only " + st.getAvailableSeats() + " seats available, requested " + request.getNumberOfTickets(),
                "INSUFFICIENT_SEATS");
        }

        // Business rule: validate seat count matches requested seats list
        if (request.getRequestedSeats().size() != request.getNumberOfTickets()) {
            throw new BusinessException(
                "Number of tickets (" + request.getNumberOfTickets() +
                ") must match number of selected seats (" + request.getRequestedSeats().size() + ")",
                "SEAT_COUNT_MISMATCH");
        }

        // Business rule: validate all requested seats are available
        Map<String, Boolean> seatMap = ticketingRepository.getSeatMap(request.getShowtimeId());
        List<String> unavailable = request.getRequestedSeats().stream()
                .filter(seat -> !seatMap.containsKey(seat) || Boolean.TRUE.equals(seatMap.get(seat)))
                .toList();
        if (!unavailable.isEmpty()) {
            throw new BusinessException(
                "Seats already taken or invalid: " + unavailable,
                "SEATS_UNAVAILABLE");
        }

        // Compute price
        BigDecimal unitPrice = "PREMIUM".equals(request.getTicketType())
                ? st.getPremiumPrice()
                : st.getStandardPrice();
        BigDecimal total = unitPrice.multiply(BigDecimal.valueOf(request.getNumberOfTickets()));

        Booking booking = Booking.builder()
                .id(ticketingRepository.nextBookingId())
                .showtimeId(request.getShowtimeId())
                .movieId(st.getMovieId())
                .cinemaId(st.getCinemaId())
                .userId(request.getUserId())
                .seatNumbers(request.getRequestedSeats())
                .numberOfTickets(request.getNumberOfTickets())
                .unitPrice(unitPrice)
                .totalAmount(total)
                .ticketType(request.getTicketType())
                .status(BookingStatus.CONFIRMED)
                .bookedAt(Instant.now())
                .expiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
                .paymentReference(request.getPaymentReference())
                .confirmationCode(UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .build();

        Booking saved = ticketingRepository.bookSeats(booking);
        log.info("Booking confirmed: {} for user {} showtime {} seats {}",
                saved.getId(), saved.getUserId(), saved.getShowtimeId(), saved.getSeatNumbers());
        return saved;
    }

    public Booking getBooking(String bookingId) {
        return ticketingRepository.findBookingById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", bookingId));
    }

    public List<Booking> getBookingsForUser(String userId) {
        return ticketingRepository.findBookingsByUser(userId);
    }

    public Booking cancelBooking(String bookingId) {
        Booking booking = getBooking(bookingId);

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BusinessException("Booking is already cancelled", "ALREADY_CANCELLED");
        }
        if (booking.getStatus() == BookingStatus.EXPIRED) {
            throw new BusinessException("Cannot cancel an expired booking", "BOOKING_EXPIRED");
        }

        ticketingRepository.cancelBooking(bookingId);
        booking.setStatus(BookingStatus.CANCELLED);
        log.info("Booking cancelled: {}", bookingId);
        return booking;
    }
}
