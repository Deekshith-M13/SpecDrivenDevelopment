package com.movieverse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("MovieVerse API Integration Tests")
class MovieVerseIntegrationTest {

    @Autowired MockMvc mvc;

    // ── Movie Catalogue ───────────────────────────────────────────────────────

    @Test @DisplayName("GET /api/v1/movies returns 200 with list")
    void getAllMovies() throws Exception {
        mvc.perform(get("/api/v1/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(10))));
    }

    @Test @DisplayName("GET /api/v1/movies/MOV-001 returns The Dark Knight")
    void getMovieById() throws Exception {
        mvc.perform(get("/api/v1/movies/MOV-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("The Dark Knight"))
                .andExpect(jsonPath("$.data.year").value(2008))
                .andExpect(jsonPath("$.data.director").value("Christopher Nolan"));
    }

    @Test @DisplayName("GET /api/v1/movies/INVALID returns 404")
    void getMovieNotFound() throws Exception {
        mvc.perform(get("/api/v1/movies/INVALID"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("RESOURCE_NOT_FOUND"));
    }

    @Test @DisplayName("GET /api/v1/movies/year/2019 returns Parasite and Endgame")
    void getByYear() throws Exception {
        mvc.perform(get("/api/v1/movies/year/2019"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)));
    }

    @Test @DisplayName("GET /api/v1/movies/genre/SCI_FI returns sci-fi movies")
    void getByGenre() throws Exception {
        mvc.perform(get("/api/v1/movies/genre/SCI_FI"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(2))));
    }

    @Test @DisplayName("GET /api/v1/movies/director?name=Nolan returns Nolan films")
    void getByDirector() throws Exception {
        mvc.perform(get("/api/v1/movies/director").param("name", "Nolan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(4))));
    }

    // ── Financial Service ─────────────────────────────────────────────────────

    @Test @DisplayName("GET /api/v1/financials/MOV-004 returns Endgame financials")
    void getFinancials_endgame() throws Exception {
        mvc.perform(get("/api/v1/financials/MOV-004"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.movieId").value("MOV-004"))
                .andExpect(jsonPath("$.data.weeklyBreakdown", hasSize(4)));
    }

    @Test @DisplayName("GET /api/v1/financials/MOV-001/weekly returns 4 weeks")
    void getWeeklyRevenue() throws Exception {
        mvc.perform(get("/api/v1/financials/MOV-001/weekly"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(4)))
                .andExpect(jsonPath("$.data[0].weekNumber").value(1));
    }

    @Test @DisplayName("GET /api/v1/financials/MOV-001/weekly/2 returns week 2")
    void getWeek2() throws Exception {
        mvc.perform(get("/api/v1/financials/MOV-001/weekly/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.weekNumber").value(2));
    }

    // ── Rating Service ────────────────────────────────────────────────────────

    @Test @DisplayName("GET /api/v1/ratings/MOV-005/summary returns Shawshank 9.3 IMDb")
    void getRatingSummary() throws Exception {
        mvc.perform(get("/api/v1/ratings/MOV-005/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.imdbScore").value(9.3))
                .andExpect(jsonPath("$.data.totalRatings").value(greaterThan(0)));
    }

    @Test @DisplayName("POST /api/v1/ratings/MOV-001 submits a new rating")
    void submitRating() throws Exception {
        String body = """
            {"userId":"test-integration-user","score":8.5,"review":"Great test!"}
            """;
        mvc.perform(post("/api/v1/ratings/MOV-001")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.score").value(8.5));
    }

    @Test @DisplayName("POST rating with invalid score returns 400")
    void submitRating_invalidScore() throws Exception {
        String body = """
            {"userId":"user-x","score":15.0}
            """;
        mvc.perform(post("/api/v1/ratings/MOV-001")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"));
    }

    @Test @DisplayName("GET /api/v1/ratings/top-rated returns a top rated movie")
    void getTopRatedMovie() throws Exception {
        mvc.perform(get("/api/v1/ratings/top-rated"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.averageScore").isNumber())
                .andExpect(jsonPath("$.data.totalRatings").isNumber());
    }

    // ── Snapshot Service ──────────────────────────────────────────────────────

    @Test @DisplayName("GET /api/v1/snapshots/MOV-002 returns Inception moments")
    void getSnapshots() throws Exception {
        mvc.perform(get("/api/v1/snapshots/MOV-002"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(4))))
                .andExpect(jsonPath("$.data[0].title").value("Paris Folding"));
    }

    @Test @DisplayName("GET /api/v1/snapshots/MOV-001/iconic returns only iconic moments")
    void getIconicSnapshots() throws Exception {
        mvc.perform(get("/api/v1/snapshots/MOV-001/iconic"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[*].isIconic", everyItem(is(true))));
    }

    // ── Recognition Service ───────────────────────────────────────────────────

    @Test @DisplayName("GET /api/v1/recognition/MOV-008/summary returns 7 Oscar wins for EEAAO")
    void getRecognitionSummary_eeaao() throws Exception {
        mvc.perform(get("/api/v1/recognition/MOV-008/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.oscarWins").value(7));
    }

    @Test @DisplayName("GET /api/v1/recognition/MOV-003/wins returns Parasite 4 Oscar wins")
    void getWins_parasite() throws Exception {
        mvc.perform(get("/api/v1/recognition/MOV-003/wins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(5))));
    }

    @Test @DisplayName("GET /api/v1/recognition/MOV-009/ceremony?name=BAFTA returns BAFTA awards")
    void getByCeremony() throws Exception {
        mvc.perform(get("/api/v1/recognition/MOV-009/ceremony").param("name", "BAFTA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].ceremonyName", containsString("BAFTA")));
    }

    // ── Ticketing Service ─────────────────────────────────────────────────────

    @Test @DisplayName("GET /api/v1/ticketing/cinemas returns 3 cinemas")
    void getCinemas() throws Exception {
        mvc.perform(get("/api/v1/ticketing/cinemas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(3)));
    }

    @Test @DisplayName("GET /api/v1/ticketing/movies/MOV-009/showtimes returns Oppenheimer shows")
    void getShowtimesForMovie() throws Exception {
        mvc.perform(get("/api/v1/ticketing/movies/MOV-009/showtimes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(4))));
    }

    @Test @DisplayName("GET /api/v1/ticketing/showtimes/SHW-001/availability returns seat map")
    void getSeatAvailability() throws Exception {
        mvc.perform(get("/api/v1/ticketing/showtimes/SHW-001/availability"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.totalSeats").value(250))
                .andExpect(jsonPath("$.data.availableSeatNumbers").isArray());
    }

    @Test @DisplayName("POST /api/v1/ticketing/bookings creates confirmed booking")
    void bookTickets_success() throws Exception {
                String body = """
                        {
                            "showtimeId": "SHW-004",
                            "userId": "integ-user-1",
                            "numberOfTickets": 2,
                            "requestedSeats": ["C5","C6"],
                            "ticketType": "STANDARD",
                            "paymentReference": "PAY-TEST-9999"
                        }
                        """;
        mvc.perform(post("/api/v1/ticketing/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.status").value("CONFIRMED"))
                .andExpect(jsonPath("$.data.confirmationCode").isNotEmpty())
                .andExpect(jsonPath("$.data.totalAmount").isNotEmpty());
    }

    @Test @DisplayName("POST booking with missing fields returns 400 validation error")
    void bookTickets_missingFields() throws Exception {
        String body = """
            {"showtimeId": "SHW-001"}
            """;
        mvc.perform(post("/api/v1/ticketing/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"));
    }
}
