package com.movieverse.snapshot.service;

import com.movieverse.common.exception.ResourceNotFoundException;
import com.movieverse.movie.service.MovieService;
import com.movieverse.snapshot.model.Snapshot;
import com.movieverse.snapshot.model.SnapshotCategory;
import com.movieverse.snapshot.repository.SnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SnapshotService {

    private final SnapshotRepository snapshotRepository;
    private final MovieService movieService;

    public List<Snapshot> getSnapshots(String movieId) {
        movieService.validateMovieExists(movieId);
        return snapshotRepository.findByMovieId(movieId).stream()
                .sorted(Comparator.comparingInt(Snapshot::getPopularityRank))
                .collect(Collectors.toList());
    }

    public Snapshot getSnapshot(String movieId, String snapshotId) {
        movieService.validateMovieExists(movieId);
        return snapshotRepository.findById(movieId, snapshotId)
                .orElseThrow(() -> new ResourceNotFoundException("Snapshot", snapshotId));
    }

    public List<Snapshot> getIconicSnapshots(String movieId) {
        movieService.validateMovieExists(movieId);
        return snapshotRepository.findByMovieId(movieId).stream()
                .filter(Snapshot::isIconic)
                .sorted(Comparator.comparingInt(Snapshot::getPopularityRank))
                .collect(Collectors.toList());
    }

    public List<Snapshot> getByCategory(String movieId, SnapshotCategory category) {
        movieService.validateMovieExists(movieId);
        return snapshotRepository.findByCategory(movieId, category);
    }
}
