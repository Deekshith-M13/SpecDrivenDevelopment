package com.movieverse.recognition.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Award {
    private String id;
    private String movieId;
    private String ceremonyName;     // e.g. "Academy Awards (Oscars)"
    private int ceremonyYear;        // Year ceremony was held
    private String category;         // e.g. "Best Picture"
    private String nominee;          // Person or film nominated
    private boolean won;
    private String presentedBy;      // Organization
    private int ceremonyEdition;     // e.g. 81st Academy Awards
}
