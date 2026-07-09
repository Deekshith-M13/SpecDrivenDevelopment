package com.movieverse.snapshot.controller;

import com.movieverse.common.response.ApiResponse;
import com.movieverse.snapshot.model.Snapshot;
import com.movieverse.snapshot.model.SnapshotCategory;
import com.movieverse.snapshot.service.SnapshotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/snapshots")
@RequiredArgsConstructor
@Tag(name = "Snapshot Service", description = "Iconic movie moments and memorable scenes")
public class SnapshotController {

    private final SnapshotService snapshotService;

    @GetMapping("/{movieId}")
    @Operation(summary = "Get all snapshots for a movie, ordered by popularity rank")
    public ResponseEntity<ApiResponse<List<Snapshot>>> getSnapshots(@PathVariable String movieId) {
        return ResponseEntity.ok(ApiResponse.success(snapshotService.getSnapshots(movieId)));
    }

    @GetMapping("/{movieId}/{snapshotId}")
    @Operation(summary = "Get a specific snapshot")
    public ResponseEntity<ApiResponse<Snapshot>> getSnapshot(
            @PathVariable String movieId, @PathVariable String snapshotId) {
        return ResponseEntity.ok(ApiResponse.success(snapshotService.getSnapshot(movieId, snapshotId)));
    }

    @GetMapping("/{movieId}/iconic")
    @Operation(summary = "Get only the most iconic moments")
    public ResponseEntity<ApiResponse<List<Snapshot>>> getIconic(@PathVariable String movieId) {
        return ResponseEntity.ok(ApiResponse.success(snapshotService.getIconicSnapshots(movieId)));
    }

    @GetMapping("/{movieId}/category/{category}")
    @Operation(summary = "Filter moments by category (CLIMAX, PLOT_TWIST, etc.)")
    public ResponseEntity<ApiResponse<List<Snapshot>>> getByCategory(
            @PathVariable String movieId, @PathVariable SnapshotCategory category) {
        return ResponseEntity.ok(ApiResponse.success(snapshotService.getByCategory(movieId, category)));
    }
}
