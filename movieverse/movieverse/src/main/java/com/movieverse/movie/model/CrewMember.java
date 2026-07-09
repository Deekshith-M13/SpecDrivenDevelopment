package com.movieverse.movie.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CrewMember {
    private String name;
    private String role;       // e.g. "Cinematographer", "Editor", "Costume Designer"
    private String department; // e.g. "Camera", "Post-Production", "Art"
}
