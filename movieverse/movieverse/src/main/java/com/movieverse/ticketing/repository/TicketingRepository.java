package com.movieverse.ticketing.repository;

import com.movieverse.ticketing.model.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class TicketingRepository {

    private final Map<String, Cinema>   cinemas   = new ConcurrentHashMap<>();
    private final Map<String, Showtime> showtimes = new ConcurrentHashMap<>();
    private final Map<String, Booking>  bookings  = new ConcurrentHashMap<>();

    // seatMap: showtimeId -> seatNumber -> isBooked
    private final Map<String, Map<String, Boolean>> seatMap = new ConcurrentHashMap<>();

    private final AtomicLong bookingSeq = new AtomicLong(1000);

    @PostConstruct
    public void seed() {
        seedCinemas();
        seedShowtimes();
        log.info("TicketingRepository seeded: {} cinemas, {} showtimes",
                cinemas.size(), showtimes.size());
    }

    // ── Cinemas ──────────────────────────────────────────────────────────────

    private void seedCinemas() {
        cinemas.put("CIN-001", Cinema.builder()
                .id("CIN-001").name("PVR INOX - Infiniti Mall").address("Infiniti Mall, Andheri West")
                .city("Mumbai")
                .screens(List.of(
                    Screen.builder().id("SCR-001-1").name("Screen 1 – IMAX").type(ScreenType.IMAX).totalSeats(250).rows(10).seatsPerRow(25).build(),
                    Screen.builder().id("SCR-001-2").name("Screen 2 – Standard").type(ScreenType.STANDARD).totalSeats(150).rows(10).seatsPerRow(15).build(),
                    Screen.builder().id("SCR-001-3").name("Screen 3 – Dolby Atmos").type(ScreenType.DOLBY_ATMOS).totalSeats(180).rows(9).seatsPerRow(20).build()
                ))
                .build());

        cinemas.put("CIN-002", Cinema.builder()
                .id("CIN-002").name("AMC Empire 25").address("234 W 42nd St")
                .city("New York City")
                .screens(List.of(
                    Screen.builder().id("SCR-002-1").name("Auditorium 1 – IMAX").type(ScreenType.IMAX).totalSeats(300).rows(12).seatsPerRow(25).build(),
                    Screen.builder().id("SCR-002-2").name("Auditorium 2").type(ScreenType.STANDARD).totalSeats(200).rows(10).seatsPerRow(20).build(),
                    Screen.builder().id("SCR-002-3").name("Auditorium 3 – VIP").type(ScreenType.VIP).totalSeats(60).rows(6).seatsPerRow(10).build()
                ))
                .build());

        cinemas.put("CIN-003", Cinema.builder()
                .id("CIN-003").name("Odeon Luxe Leicester Square").address("Leicester Square")
                .city("London")
                .screens(List.of(
                    Screen.builder().id("SCR-003-1").name("Main Auditorium").type(ScreenType.DOLBY_ATMOS).totalSeats(400).rows(16).seatsPerRow(25).build(),
                    Screen.builder().id("SCR-003-2").name("Screen 2").type(ScreenType.STANDARD).totalSeats(120).rows(8).seatsPerRow(15).build()
                ))
                .build());
    }

    // ── Showtimes ─────────────────────────────────────────────────────────────

    private void seedShowtimes() {
        LocalDateTime base = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0);

        // Oppenheimer (MOV-009) at PVR Mumbai
        addShowtime("SHW-001", "MOV-009", "CIN-001", "SCR-001-1", base, 180, new BigDecimal("750"), new BigDecimal("1200"), 250, 85);
        addShowtime("SHW-002", "MOV-009", "CIN-001", "SCR-001-1", base.withHour(14), 180, new BigDecimal("750"), new BigDecimal("1200"), 250, 120);
        addShowtime("SHW-003", "MOV-009", "CIN-001", "SCR-001-1", base.withHour(18), 180, new BigDecimal("750"), new BigDecimal("1200"), 250, 200);
        addShowtime("SHW-004", "MOV-009", "CIN-001", "SCR-001-2", base.withHour(11), 180, new BigDecimal("450"), new BigDecimal("700"), 150, 30);

        // Inception (MOV-002) at AMC New York
        addShowtime("SHW-005", "MOV-002", "CIN-002", "SCR-002-1", base.withHour(13), 148, new BigDecimal("22"), new BigDecimal("38"), 300, 140);
        addShowtime("SHW-006", "MOV-002", "CIN-002", "SCR-002-2", base.withHour(16), 148, new BigDecimal("18"), new BigDecimal("28"), 200, 50);
        addShowtime("SHW-007", "MOV-002", "CIN-002", "SCR-002-3", base.withHour(19).plusDays(1), 148, new BigDecimal("45"), new BigDecimal("65"), 60, 10);

        // Everything Everywhere (MOV-008) at Odeon London
        addShowtime("SHW-008", "MOV-008", "CIN-003", "SCR-003-1", base.withHour(15), 139, new BigDecimal("18"), new BigDecimal("28"), 400, 250);
        addShowtime("SHW-009", "MOV-008", "CIN-003", "SCR-003-2", base.withHour(20), 139, new BigDecimal("15"), new BigDecimal("25"), 120, 40);

        // Parasite (MOV-003) at AMC New York
        addShowtime("SHW-010", "MOV-003", "CIN-002", "SCR-002-2", base.withHour(17).plusDays(2), 132, new BigDecimal("18"), new BigDecimal("28"), 200, 80);

        // Dark Knight (MOV-001) at all cinemas — classic screening
        addShowtime("SHW-011", "MOV-001", "CIN-001", "SCR-001-3", base.plusDays(3).withHour(20), 152, new BigDecimal("600"), new BigDecimal("1000"), 180, 0);
        addShowtime("SHW-012", "MOV-001", "CIN-002", "SCR-002-1", base.plusDays(3).withHour(20), 152, new BigDecimal("22"), new BigDecimal("38"), 300, 0);
        addShowtime("SHW-013", "MOV-001", "CIN-003", "SCR-003-1", base.plusDays(3).withHour(20), 152, new BigDecimal("18"), new BigDecimal("30"), 400, 0);
    }

    private void addShowtime(String id, String movieId, String cinemaId, String screenId,
                             LocalDateTime start, int runtimeMins,
                             BigDecimal stdPrice, BigDecimal premPrice,
                             int totalSeats, int alreadyBooked) {
        Showtime st = Showtime.builder()
                .id(id)
                .movieId(movieId)
                .cinemaId(cinemaId)
                .screenId(screenId)
                .startTime(start)
                .endTime(start.plusMinutes(runtimeMins + 15)) // 15 min ads
                .totalSeats(totalSeats)
                .availableSeats(totalSeats - alreadyBooked)
                .standardPrice(stdPrice)
                .premiumPrice(premPrice)
                .language("English")
                .hasSubtitles(false)
                .status(totalSeats - alreadyBooked == 0 ? ShowtimeStatus.SOLD_OUT : ShowtimeStatus.ON_SALE)
                .build();
        showtimes.put(id, st);

        // Build seat map — mark some seats as already booked
        Map<String, Boolean> seats = new LinkedHashMap<>();
        String[] rowLabels = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P"};
        // Determine seats per row from screen info
        int seatsPerRow = totalSeats / Math.min(rowLabels.length, totalSeats / 15 + 1);
        seatsPerRow = Math.max(10, Math.min(seatsPerRow, 30));
        int booked = 0;
        outer:
        for (String row : rowLabels) {
            for (int col = 1; col <= seatsPerRow; col++) {
                String seatNo = row + col;
                boolean isBooked = booked < alreadyBooked;
                seats.put(seatNo, isBooked);
                if (isBooked) booked++;
                if (seats.size() >= totalSeats) break outer;
            }
        }
        seatMap.put(id, seats);
    }

    // ── Query methods ─────────────────────────────────────────────────────────

    public List<Cinema> findAllCinemas() { return new ArrayList<>(cinemas.values()); }
    public Optional<Cinema> findCinemaById(String id) { return Optional.ofNullable(cinemas.get(id)); }

    public List<Showtime> findShowtimesByMovie(String movieId) {
        return showtimes.values().stream()
                .filter(s -> s.getMovieId().equals(movieId))
                .collect(Collectors.toList());
    }

    public List<Showtime> findShowtimesByCinema(String cinemaId) {
        return showtimes.values().stream()
                .filter(s -> s.getCinemaId().equals(cinemaId))
                .collect(Collectors.toList());
    }

    public Optional<Showtime> findShowtimeById(String id) {
        return Optional.ofNullable(showtimes.get(id));
    }

    public Map<String, Boolean> getSeatMap(String showtimeId) {
        return seatMap.getOrDefault(showtimeId, Collections.emptyMap());
    }

    public List<String> getAvailableSeats(String showtimeId) {
        return seatMap.getOrDefault(showtimeId, Collections.emptyMap())
                .entrySet().stream()
                .filter(e -> !e.getValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public synchronized Booking bookSeats(Booking booking) {
        String showtimeId = booking.getShowtimeId();
        Showtime st = showtimes.get(showtimeId);
        Map<String, Boolean> seats = seatMap.get(showtimeId);

        // Mark seats as booked
        booking.getSeatNumbers().forEach(seat -> seats.put(seat, true));

        // Decrement available seats
        st.setAvailableSeats(st.getAvailableSeats() - booking.getNumberOfTickets());
        if (st.getAvailableSeats() == 0) {
            st.setStatus(ShowtimeStatus.SOLD_OUT);
        }

        bookings.put(booking.getId(), booking);
        return booking;
    }

    public Optional<Booking> findBookingById(String id) {
        return Optional.ofNullable(bookings.get(id));
    }

    public List<Booking> findBookingsByUser(String userId) {
        return bookings.values().stream()
                .filter(b -> b.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public synchronized void cancelBooking(String bookingId) {
        Booking b = bookings.get(bookingId);
        if (b == null) return;
        b.setStatus(BookingStatus.CANCELLED);

        // Release seats
        Showtime st = showtimes.get(b.getShowtimeId());
        Map<String, Boolean> seats = seatMap.get(b.getShowtimeId());
        b.getSeatNumbers().forEach(seat -> seats.put(seat, false));
        st.setAvailableSeats(st.getAvailableSeats() + b.getNumberOfTickets());
        if (st.getStatus() == ShowtimeStatus.SOLD_OUT) {
            st.setStatus(ShowtimeStatus.ON_SALE);
        }
    }

    public String nextBookingId() {
        return "BKG-" + Long.toHexString(bookingSeq.getAndIncrement()).toUpperCase();
    }
}
