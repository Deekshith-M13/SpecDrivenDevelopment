package com.movieverse.snapshot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Snapshot {
    private String id;
    private String movieId;
    private String title;           // e.g. "The Pencil Trick"
    private String description;     // description of the moment
    private String timestampInFilm; // e.g. "00:18:45"
    private String quote;           // iconic dialogue if applicable
    private String sceneContext;    // location/context of the scene
    private SnapshotCategory category;
    private boolean isIconic;
    private int popularityRank;     // 1 = most iconic
}
