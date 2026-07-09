package com.movieverse.movie.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Movie {
    private String id;
    private String title;
    private String originalTitle;
    private int year;
    private LocalDate releaseDate;
    private Genre genre;
    private List<Genre> additionalGenres;
    private String synopsis;
    private int runtimeMinutes;
    private String language;
    private String country;
    private String studio;
    private String distributor;
    private String director;
    private List<String> writers;
    private List<String> producers;
    private List<CastMember> cast;
    private List<CrewMember> crew;
    private String posterUrl;
    private String trailerUrl;
    private String mpaaRating; // G, PG, PG-13, R, NC-17
    private MovieStatus status;
}
