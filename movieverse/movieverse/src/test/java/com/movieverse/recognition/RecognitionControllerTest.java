package com.movieverse.recognition;

import com.movieverse.common.response.ApiResponse;
import com.movieverse.recognition.controller.RecognitionController;
import com.movieverse.recognition.model.AwardLeaderResponse;
import com.movieverse.recognition.service.RecognitionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RecognitionController Unit Tests")
class RecognitionControllerTest {

    @Mock private RecognitionService recognitionService;
    @InjectMocks private RecognitionController recognitionController;

    @Test
    @DisplayName("getAwardLeader returns the recognized award leader")
    void getAwardLeader_returnsApiResponse() {
        when(recognitionService.getAwardLeader()).thenReturn(
                AwardLeaderResponse.builder()
                        .movieTitle("Oppenheimer")
                        .totalAwards(13)
                        .oscarWins(7)
                        .baftaWins(1)
                        .build()
        );

        ResponseEntity<ApiResponse<AwardLeaderResponse>> response = recognitionController.getAwardLeader();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isSuccess()).isTrue();
        assertThat(response.getBody().getData().getMovieTitle()).isEqualTo("Oppenheimer");
        assertThat(response.getBody().getData().getTotalAwards()).isEqualTo(13);
        assertThat(response.getBody().getData().getOscarWins()).isEqualTo(7);
        assertThat(response.getBody().getData().getBaftaWins()).isEqualTo(1);
    }
}
