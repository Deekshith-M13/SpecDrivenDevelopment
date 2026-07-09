package com.movieverse.financial.controller;

import com.movieverse.common.response.ApiResponse;
import com.movieverse.financial.model.MovieFinancials;
import com.movieverse.financial.model.WeeklyRevenue;
import com.movieverse.financial.service.FinancialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/financials")
@RequiredArgsConstructor
@Tag(name = "Financial Service", description = "Box office budget vs revenue analytics")
public class FinancialController {

    private final FinancialService financialService;

    @GetMapping
    @Operation(summary = "All movies financial overview")
    public ResponseEntity<ApiResponse<List<MovieFinancials>>> getAllFinancials() {
        return ResponseEntity.ok(ApiResponse.success(financialService.getAllFinancials()));
    }

    @GetMapping("/{movieId}")
    @Operation(summary = "Full financial profile for a movie")
    public ResponseEntity<ApiResponse<MovieFinancials>> getFinancials(@PathVariable String movieId) {
        return ResponseEntity.ok(ApiResponse.success(financialService.getFinancials(movieId)));
    }

    @GetMapping("/{movieId}/weekly")
    @Operation(summary = "4-week weekly box office breakdown")
    public ResponseEntity<ApiResponse<List<WeeklyRevenue>>> getWeeklyRevenue(@PathVariable String movieId) {
        return ResponseEntity.ok(ApiResponse.success(financialService.getWeeklyRevenue(movieId)));
    }

    @GetMapping("/{movieId}/weekly/{week}")
    @Operation(summary = "Single week revenue (1-4)")
    public ResponseEntity<ApiResponse<WeeklyRevenue>> getWeek(
            @PathVariable String movieId,
            @PathVariable int week) {
        return ResponseEntity.ok(ApiResponse.success(financialService.getWeekByNumber(movieId, week)));
    }
}
