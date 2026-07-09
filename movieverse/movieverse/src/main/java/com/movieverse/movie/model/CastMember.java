package com.movieverse.movie.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CastMember {
    private String actorName;
    private String characterName;
    private boolean isLead;
    private int billingOrder;
}
