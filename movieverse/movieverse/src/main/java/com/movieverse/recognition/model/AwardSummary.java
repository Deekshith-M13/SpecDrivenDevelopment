package com.movieverse.recognition.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class AwardSummary {
    private String movieId;
    private String movieTitle;
    private int totalNominations;
    private int totalWins;
    private List<Award> wins;
    private List<Award> nominations;
    private Map<String, Long> winsByCeremony;       // ceremony -> win count
    private Map<String, Long> nominationsByCeremony;
    private boolean isMultipleOscarWinner;
    private int oscarWins;
    private int oscarNominations;
}
