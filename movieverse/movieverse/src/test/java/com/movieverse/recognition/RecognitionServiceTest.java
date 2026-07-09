package com.movieverse.recognition;

import com.movieverse.movie.model.Movie;
import com.movieverse.movie.service.MovieService;
import com.movieverse.recognition.model.Award;
import com.movieverse.recognition.repository.AwardRepository;
import com.movieverse.recognition.service.RecognitionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RecognitionService Unit Tests")
class RecognitionServiceTest {

    @Mock private AwardRepository awardRepository;
    @Mock private MovieService movieService;
    @InjectMocks private RecognitionService recognitionService;

    @Test
    @DisplayName("getAwardLeader returns the movie with the highest total awards")
    void getAwardLeader_returnsMovieWithHighestAwardCount() {
        when(awardRepository.findAllMovieAwards()).thenReturn(List.of(
                award("MOV-001", "The Dark Knight", true, "Academy Awards", 1),
                award("MOV-001", "The Dark Knight", true, "Academy Awards", 1),
                award("MOV-001", "The Dark Knight", true, "BAFTA Awards", 1),
                award("MOV-002", "Inception", true, "Academy Awards", 1),
                award("MOV-002", "Inception", true, "Academy Awards", 1),
                award("MOV-002", "Inception", true, "BAFTA Awards", 1),
                award("MOV-002", "Inception", true, "BAFTA Awards", 1)
        ));
        when(movieService.getMovieById("MOV-001")).thenReturn(Movie.builder().title("The Dark Knight").build());
        when(movieService.getMovieById("MOV-002")).thenReturn(Movie.builder().title("Inception").build());

        var result = recognitionService.getAwardLeader();

        assertThat(result.getMovieTitle()).isEqualTo("Inception");
        assertThat(result.getTotalAwards()).isEqualTo(4);
        assertThat(result.getOscarWins()).isEqualTo(2);
        assertThat(result.getBaftaWins()).isEqualTo(2);
    }

    private Award award(String movieId, String movieTitle, boolean won, String ceremonyName, int count) {
        return Award.builder()
                .movieId(movieId)
                .nominee(movieTitle)
                .won(won)
                .ceremonyName(ceremonyName)
                .build();
    }
}
