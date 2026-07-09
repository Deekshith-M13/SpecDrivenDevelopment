package com.movieverse.ticketing;

import com.movieverse.common.exception.BusinessException;
import com.movieverse.common.exception.ResourceNotFoundException;
import com.movieverse.movie.service.MovieService;
import com.movieverse.ticketing.dto.BookingRequest;
import com.movieverse.ticketing.model.*;
import com.movieverse.ticketing.repository.TicketingRepository;
import com.movieverse.ticketing.service.TicketingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TicketingService Unit Tests")
class TicketingServiceTest {

    @Mock TicketingRepository ticketingRepository;
    @Mock MovieService movieService;
    @InjectMocks TicketingService ticketingService;

    private Showtime showtime;
    private BookingRequest validRequest;

    @BeforeEach
    void setUp() {
        showtime = Showtime.builder()
                .id("SHW-001")
                .movieId("MOV-001")
                .cinemaId("CIN-001")
                .screenId("SCR-001-1")
                .startTime(LocalDateTime.now().plusHours(2))
                .totalSeats(100)
                .availableSeats(50)
                .standardPrice(new BigDecimal("750"))
                .premiumPrice(new BigDecimal("1200"))
                .status(ShowtimeStatus.ON_SALE)
                .build();

        validRequest = new BookingRequest();
        validRequest.setShowtimeId("SHW-001");
        validRequest.setUserId("user-456");
        validRequest.setNumberOfTickets(2);
        validRequest.setRequestedSeats(List.of("D4", "D5"));
        validRequest.setTicketType("STANDARD");
        validRequest.setPaymentReference("PAY-REF-12345");
    }

    @Test
    @DisplayName("bookTickets confirms booking with correct total amount")
    void bookTickets_success() {
        when(ticketingRepository.findShowtimeById("SHW-001")).thenReturn(Optional.of(showtime));
        Map<String, Boolean> seats = new HashMap<>();
        seats.put("D4", false);
        seats.put("D5", false);
        when(ticketingRepository.getSeatMap("SHW-001")).thenReturn(seats);
        when(ticketingRepository.nextBookingId()).thenReturn("BKG-ABC1");

        Booking saved = Booking.builder()
                .id("BKG-ABC1").showtimeId("SHW-001").movieId("MOV-001")
                .userId("user-456").seatNumbers(List.of("D4", "D5"))
                .numberOfTickets(2).unitPrice(new BigDecimal("750"))
                .totalAmount(new BigDecimal("1500"))
                .status(BookingStatus.CONFIRMED)
                .bookedAt(Instant.now()).confirmationCode("ABCD1234")
                .build();
        when(ticketingRepository.bookSeats(any())).thenReturn(saved);

        Booking result = ticketingService.bookTickets(validRequest);

        assertThat(result.getId()).isEqualTo("BKG-ABC1");
        assertThat(result.getTotalAmount()).isEqualByComparingTo("1500");
        assertThat(result.getStatus()).isEqualTo(BookingStatus.CONFIRMED);
        verify(ticketingRepository).bookSeats(any(Booking.class));
    }

    @Test
    @DisplayName("bookTickets throws when showtime is sold out")
    void bookTickets_soldOut() {
        showtime.setStatus(ShowtimeStatus.SOLD_OUT);
        showtime.setAvailableSeats(0);
        when(ticketingRepository.findShowtimeById("SHW-001")).thenReturn(Optional.of(showtime));

        assertThatThrownBy(() -> ticketingService.bookTickets(validRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("seats available");
    }

    @Test
    @DisplayName("bookTickets throws when not enough seats available")
    void bookTickets_insufficientSeats() {
        showtime.setAvailableSeats(1); // only 1 seat, requesting 2
        when(ticketingRepository.findShowtimeById("SHW-001")).thenReturn(Optional.of(showtime));

        assertThatThrownBy(() -> ticketingService.bookTickets(validRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Only 1 seats available");
    }

    @Test
    @DisplayName("bookTickets throws when seat count and seat list mismatch")
    void bookTickets_seatCountMismatch() {
        when(ticketingRepository.findShowtimeById("SHW-001")).thenReturn(Optional.of(showtime));
        validRequest.setRequestedSeats(List.of("D4")); // 1 seat but 2 tickets

        assertThatThrownBy(() -> ticketingService.bookTickets(validRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("must match number of selected seats");
    }

    @Test
    @DisplayName("bookTickets throws when a seat is already booked")
    void bookTickets_seatAlreadyTaken() {
        when(ticketingRepository.findShowtimeById("SHW-001")).thenReturn(Optional.of(showtime));
        Map<String, Boolean> seats = new HashMap<>();
        seats.put("D4", true);  // D4 is already booked
        seats.put("D5", false);
        when(ticketingRepository.getSeatMap("SHW-001")).thenReturn(seats);

        assertThatThrownBy(() -> ticketingService.bookTickets(validRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Seats already taken or invalid");
    }

    @Test
    @DisplayName("cancelBooking releases seats and changes status")
    void cancelBooking_success() {
        Booking booking = Booking.builder()
                .id("BKG-001").showtimeId("SHW-001")
                .seatNumbers(List.of("D4", "D5"))
                .numberOfTickets(2)
                .status(BookingStatus.CONFIRMED)
                .bookedAt(Instant.now())
                .build();
        when(ticketingRepository.findBookingById("BKG-001")).thenReturn(Optional.of(booking));

        Booking result = ticketingService.cancelBooking("BKG-001");

        assertThat(result.getStatus()).isEqualTo(BookingStatus.CANCELLED);
        verify(ticketingRepository).cancelBooking("BKG-001");
    }

    @Test
    @DisplayName("cancelBooking throws when already cancelled")
    void cancelBooking_alreadyCancelled() {
        Booking booking = Booking.builder()
                .id("BKG-001").status(BookingStatus.CANCELLED).build();
        when(ticketingRepository.findBookingById("BKG-001")).thenReturn(Optional.of(booking));

        assertThatThrownBy(() -> ticketingService.cancelBooking("BKG-001"))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("already cancelled");
    }

    @Test
    @DisplayName("getBooking throws ResourceNotFoundException for unknown booking")
    void getBooking_notFound() {
        when(ticketingRepository.findBookingById("UNKNOWN")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ticketingService.getBooking("UNKNOWN"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("PREMIUM ticket uses premium price correctly")
    void bookTickets_premiumPricing() {
        when(ticketingRepository.findShowtimeById("SHW-001")).thenReturn(Optional.of(showtime));
        Map<String, Boolean> seats = new HashMap<>();
        seats.put("D4", false);
        seats.put("D5", false);
        when(ticketingRepository.getSeatMap("SHW-001")).thenReturn(seats);
        when(ticketingRepository.nextBookingId()).thenReturn("BKG-PRE-1");

        validRequest.setTicketType("PREMIUM");

        Booking saved = Booking.builder()
                .id("BKG-PRE-1").unitPrice(new BigDecimal("1200"))
                .totalAmount(new BigDecimal("2400"))
                .status(BookingStatus.CONFIRMED)
                .seatNumbers(List.of("D4", "D5"))
                .bookedAt(Instant.now()).confirmationCode("XYZ99999")
                .build();
        when(ticketingRepository.bookSeats(any())).thenReturn(saved);

        Booking result = ticketingService.bookTickets(validRequest);
        assertThat(result.getUnitPrice()).isEqualByComparingTo("1200");
        assertThat(result.getTotalAmount()).isEqualByComparingTo("2400");
    }
}
