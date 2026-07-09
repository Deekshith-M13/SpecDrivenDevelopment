package com.movieverse.financial.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class WeeklyRevenue {
    private int weekNumber;       // 1-4
    private LocalDate weekStart;
    private LocalDate weekEnd;
    private BigDecimal domestic;
    private BigDecimal international;
    private BigDecimal total;
    private int theatreCount;
    private BigDecimal perTheatreAverage;
    private int rank;             // Box office rank that week
    private BigDecimal cumulativeTotal;
}
