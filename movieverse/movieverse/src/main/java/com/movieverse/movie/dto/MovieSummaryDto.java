package com.movieverse.movie.dto;

import com.movieverse.movie.model.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class MovieSummaryDto {
    private String id;
    private String title;
    private int year;
    private LocalDate releaseDate;
    private Genre genre;
    private List<Genre> additionalGenres;
    private String director;
    private String synopsis;
    private int runtimeMinutes;
    private String mpaaRating;
    private MovieStatus status;
}
