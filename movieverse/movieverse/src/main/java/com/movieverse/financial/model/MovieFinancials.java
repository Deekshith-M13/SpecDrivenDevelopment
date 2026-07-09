package com.movieverse.financial.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class MovieFinancials {
    private String movieId;
    private BigDecimal productionBudget;    // USD
    private BigDecimal marketingBudget;     // USD
    private BigDecimal totalBudget;         // productionBudget + marketingBudget
    private BigDecimal domesticRevenue;     // USA + Canada
    private BigDecimal internationalRevenue;
    private BigDecimal totalRevenue;
    private BigDecimal streamingRevenue;
    private BigDecimal merchandisingRevenue;
    private List<WeeklyRevenue> weeklyBreakdown; // 4 weeks post-release
    private BigDecimal breakEvenPoint;      // typically 2.5x production budget
    private String currency;
    private boolean isProfitable;
    private BigDecimal roi; // Return on Investment %
}
