package com.movieverse.ticketing.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Cinema {
    private String id;
    private String name;
    private String address;
    private String city;
    private List<Screen> screens;
}
