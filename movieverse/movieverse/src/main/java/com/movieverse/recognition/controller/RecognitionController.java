package com.movieverse.recognition.controller;

import com.movieverse.common.response.ApiResponse;
import com.movieverse.recognition.model.Award;
import com.movieverse.recognition.model.AwardLeaderResponse;
import com.movieverse.recognition.model.AwardSummary;
import com.movieverse.recognition.service.RecognitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recognition")
@RequiredArgsConstructor
@Tag(name = "Recognition Service", description = "Awards and nominations tracker")
public class RecognitionController {

    private final RecognitionService recognitionService;

    @GetMapping("/award-leader")
    @Operation(summary = "Get the movie with the highest number of awards")
    public ResponseEntity<ApiResponse<AwardLeaderResponse>> getAwardLeader() {
        return ResponseEntity.ok(ApiResponse.success(recognitionService.getAwardLeader()));
    }

    @GetMapping("/{movieId}/summary")
    @Operation(summary = "Full awards summary with Oscar count and ceremony breakdown")
    public ResponseEntity<ApiResponse<AwardSummary>> getSummary(@PathVariable String movieId) {
        return ResponseEntity.ok(ApiResponse.success(recognitionService.getSummary(movieId)));
    }

    @GetMapping("/{movieId}/awards")
    @Operation(summary = "All nominations and wins for a movie")
    public ResponseEntity<ApiResponse<List<Award>>> getAllAwards(@PathVariable String movieId) {
        return ResponseEntity.ok(ApiResponse.success(recognitionService.getAllAwards(movieId)));
    }

    @GetMapping("/{movieId}/wins")
    @Operation(summary = "Only the wins for a movie")
    public ResponseEntity<ApiResponse<List<Award>>> getWins(@PathVariable String movieId) {
        return ResponseEntity.ok(ApiResponse.success(recognitionService.getWins(movieId)));
    }

    @GetMapping("/{movieId}/ceremony")
    @Operation(summary = "Filter awards by ceremony name (e.g. Academy, BAFTA, Cannes)")
    public ResponseEntity<ApiResponse<List<Award>>> getByCeremony(
            @PathVariable String movieId, @RequestParam String name) {
        return ResponseEntity.ok(ApiResponse.success(recognitionService.getByCeremony(movieId, name)));
    }
}
