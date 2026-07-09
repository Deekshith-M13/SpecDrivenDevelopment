package com.movieverse.recognition.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AwardLeaderResponse {
    private String movieTitle;
    private int totalAwards;
    private int oscarWins;
    private int baftaWins;
}
