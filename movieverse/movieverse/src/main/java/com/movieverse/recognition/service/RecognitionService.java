package com.movieverse.recognition.service;

import com.movieverse.movie.service.MovieService;
import com.movieverse.recognition.model.Award;
import com.movieverse.recognition.model.AwardLeaderResponse;
import com.movieverse.recognition.model.AwardSummary;
import com.movieverse.recognition.repository.AwardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecognitionService {

    private final AwardRepository awardRepository;
    private final MovieService movieService;

    public AwardSummary getSummary(String movieId) {
        var movie = movieService.getMovieById(movieId);
        List<Award> all = awardRepository.findByMovieId(movieId);
        List<Award> wins = all.stream().filter(Award::isWon).collect(Collectors.toList());
        List<Award> noms = all.stream().filter(a -> !a.isWon()).collect(Collectors.toList());

        var winsByCeremony = wins.stream()
                .collect(Collectors.groupingBy(Award::getCeremonyName, Collectors.counting()));
        var nomsByCeremony = noms.stream()
                .collect(Collectors.groupingBy(Award::getCeremonyName, Collectors.counting()));

        long oscarWins = wins.stream().filter(a -> a.getCeremonyName().contains("Academy")).count();
        long oscarNoms = all.stream().filter(a -> a.getCeremonyName().contains("Academy")).count();

        return AwardSummary.builder()
                .movieId(movieId)
                .movieTitle(movie.getTitle())
                .totalNominations(all.size())
                .totalWins(wins.size())
                .wins(wins)
                .nominations(noms)
                .winsByCeremony(winsByCeremony)
                .nominationsByCeremony(nomsByCeremony)
                .isMultipleOscarWinner(oscarWins > 1)
                .oscarWins((int) oscarWins)
                .oscarNominations((int) oscarNoms)
                .build();
    }

    public List<Award> getWins(String movieId) {
        movieService.validateMovieExists(movieId);
        return awardRepository.findByMovieId(movieId).stream()
                .filter(Award::isWon).collect(Collectors.toList());
    }

    public List<Award> getAllAwards(String movieId) {
        movieService.validateMovieExists(movieId);
        return awardRepository.findByMovieId(movieId);
    }

    public List<Award> getByCeremony(String movieId, String ceremony) {
        movieService.validateMovieExists(movieId);
        return awardRepository.findByMovieId(movieId).stream()
                .filter(a -> a.getCeremonyName().toLowerCase().contains(ceremony.toLowerCase()))
                .collect(Collectors.toList());
    }

    public AwardLeaderResponse getAwardLeader() {
        Map<String, List<Award>> awardsByMovie = awardRepository.findAllMovieAwards().stream()
                .collect(Collectors.groupingBy(Award::getMovieId));

        return awardsByMovie.entrySet().stream()
                .map(entry -> buildLeaderResponse(entry.getKey(), entry.getValue()))
                .max(Comparator.comparingInt(AwardLeaderResponse::getTotalAwards))
                .orElseGet(() -> AwardLeaderResponse.builder().movieTitle("N/A").totalAwards(0).oscarWins(0).baftaWins(0).build());
    }

    private AwardLeaderResponse buildLeaderResponse(String movieId, List<Award> awards) {
        var movieTitle = movieService.getMovieById(movieId).getTitle();

        long oscarWins = awards.stream()
                .filter(Award::isWon)
                .filter(a -> a.getCeremonyName() != null && a.getCeremonyName().toLowerCase().contains("academy"))
                .count();

        long baftaWins = awards.stream()
                .filter(Award::isWon)
                .filter(a -> a.getCeremonyName() != null && a.getCeremonyName().toLowerCase().contains("bafta"))
                .count();

        return AwardLeaderResponse.builder()
                .movieTitle(movieTitle)
                .totalAwards(awards.size())
                .oscarWins((int) oscarWins)
                .baftaWins((int) baftaWins)
                .build();
    }
}
