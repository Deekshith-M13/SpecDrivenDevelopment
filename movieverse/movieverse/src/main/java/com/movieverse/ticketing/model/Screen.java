package com.movieverse.ticketing.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Screen {
    private String id;
    private String name;       // "Screen 1", "IMAX Hall"
    private ScreenType type;
    private int totalSeats;
    private int rows;
    private int seatsPerRow;
}
