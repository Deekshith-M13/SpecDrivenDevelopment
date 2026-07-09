package com.movieverse.financial.repository;

import com.movieverse.financial.model.MovieFinancials;
import com.movieverse.financial.model.WeeklyRevenue;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class FinancialRepository {

    private final Map<String, MovieFinancials> store = new ConcurrentHashMap<>();

    @PostConstruct
    public void seed() {

        // The Dark Knight (2008) - Budget: $185M, Gross: $1.005B
        store.put("MOV-001", buildFinancials("MOV-001",
            185_000_000, 70_000_000, 534_858_444, 469_700_000,
            LocalDate.of(2008, 7, 18),
            new long[]{67_165_092, 43_576_016, 26_248_757, 16_122_046},
            new long[]{112_740_000, 85_000_000, 62_000_000, 41_000_000},
            new int[]{4366, 4382, 4370, 4120},
            new int[]{1, 1, 1, 1}));

        // Inception (2010) - Budget: $160M, Gross: $836.8M
        store.put("MOV-002", buildFinancials("MOV-002",
            160_000_000, 60_000_000, 292_576_195, 544_231_891,
            LocalDate.of(2010, 7, 16),
            new long[]{62_785_337, 35_640_155, 21_990_000, 14_000_000},
            new long[]{90_000_000, 68_000_000, 45_000_000, 32_000_000},
            new int[]{3792, 3845, 3790, 3560},
            new int[]{1, 1, 2, 2}));

        // Parasite (2019) - Budget: $11.4M, Gross: $258.7M
        store.put("MOV-003", buildFinancials("MOV-003",
            11_400_000, 5_000_000, 53_367_844, 205_396_945,
            LocalDate.of(2019, 10, 11),
            new long[]{393_012, 1_080_016, 2_050_000, 5_200_000},
            new long[]{4_200_000, 8_100_000, 12_000_000, 18_000_000},
            new int[]{33, 132, 620, 1500},
            new int[]{22, 10, 5, 3}));

        // Avengers: Endgame (2019) - Budget: $356M, Gross: $2.798B
        store.put("MOV-004", buildFinancials("MOV-004",
            356_000_000, 200_000_000, 858_373_000, 1_939_627_000,
            LocalDate.of(2019, 4, 26),
            new long[]{357_115_007, 145_836_195, 93_000_000, 65_000_000},
            new long[]{627_000_000, 395_000_000, 280_000_000, 195_000_000},
            new int[]{4662, 4661, 4600, 4350},
            new int[]{1, 1, 1, 1}));

        // Shawshank (1994) - Budget: $25M, Gross: $58.3M (theatrical; massive home video)
        store.put("MOV-005", buildFinancials("MOV-005",
            25_000_000, 10_000_000, 16_000_000, 42_300_000,
            LocalDate.of(1994, 9, 23),
            new long[]{727_327, 988_000, 1_100_000, 1_300_000},
            new long[]{3_200_000, 4_500_000, 5_800_000, 6_900_000},
            new int[]{33, 44, 60, 75},
            new int[]{51, 38, 22, 14}));

        // Interstellar (2014) - Budget: $165M, Gross: $701.8M
        store.put("MOV-006", buildFinancials("MOV-006",
            165_000_000, 65_000_000, 188_020_017, 513_800_000,
            LocalDate.of(2014, 11, 7),
            new long[]{47_510_360, 29_181_958, 19_260_000, 13_100_000},
            new long[]{75_000_000, 58_000_000, 44_000_000, 35_000_000},
            new int[]{3561, 3610, 3530, 3200},
            new int[]{1, 1, 2, 2}));

        // The Godfather (1972) - Budget: $6M, Gross: $245.1M
        store.put("MOV-007", buildFinancials("MOV-007",
            6_000_000, 1_500_000, 134_966_411, 110_182_589,
            LocalDate.of(1972, 3, 24),
            new long[]{865_727, 1_200_000, 1_500_000, 1_800_000},
            new long[]{5_000_000, 7_500_000, 9_000_000, 11_000_000},
            new int[]{5, 12, 22, 35},
            new int[]{3, 2, 1, 1}));

        // Everything Everywhere (2022) - Budget: $14.3M, Gross: $139.3M
        store.put("MOV-008", buildFinancials("MOV-008",
            14_300_000, 8_000_000, 69_700_000, 69_600_000,
            LocalDate.of(2022, 3, 25),
            new long[]{197_227, 1_155_998, 3_700_000, 8_200_000},
            new long[]{1_800_000, 4_600_000, 8_900_000, 16_000_000},
            new int[]{10, 38, 365, 1200},
            new int[]{14, 5, 4, 3}));

        // Oppenheimer (2023) - Budget: $100M, Gross: $952.8M
        store.put("MOV-009", buildFinancials("MOV-009",
            100_000_000, 50_000_000, 330_500_000, 622_300_000,
            LocalDate.of(2023, 7, 21),
            new long[]{82_400_000, 62_600_000, 46_500_000, 33_200_000},
            new long[]{137_300_000, 108_700_000, 82_500_000, 58_900_000},
            new int[]{3610, 3626, 3550, 3100},
            new int[]{2, 2, 1, 1}));

        // Pulp Fiction (1994) - Budget: $8M, Gross: $213.9M
        store.put("MOV-010", buildFinancials("MOV-010",
            8_000_000, 3_000_000, 107_928_762, 105_999_900,
            LocalDate.of(1994, 10, 14),
            new long[]{9_311_882, 8_800_000, 7_100_000, 5_900_000},
            new long[]{12_000_000, 10_500_000, 8_200_000, 6_500_000},
            new int[]{1338, 1430, 1400, 1300},
            new int[]{1, 1, 1, 2}));

        log.info("FinancialRepository seeded with {} records", store.size());
    }

    private MovieFinancials buildFinancials(
            String movieId,
            long productionBudget, long marketingBudget,
            long domestic, long international,
            LocalDate releaseDate,
            long[] domesticWeekly, long[] intlWeekly,
            int[] theatres, int[] ranks) {

        BigDecimal prod = BigDecimal.valueOf(productionBudget);
        BigDecimal mktg = BigDecimal.valueOf(marketingBudget);
        BigDecimal dom  = BigDecimal.valueOf(domestic);
        BigDecimal intl = BigDecimal.valueOf(international);
        BigDecimal total = dom.add(intl);
        BigDecimal totalBudget = prod.add(mktg);

        List<WeeklyRevenue> weeks = new ArrayList<>();
        BigDecimal cumulative = BigDecimal.ZERO;
        for (int i = 0; i < 4; i++) {
            BigDecimal d = BigDecimal.valueOf(domesticWeekly[i]);
            BigDecimal ii = BigDecimal.valueOf(intlWeekly[i]);
            BigDecimal wTotal = d.add(ii);
            cumulative = cumulative.add(wTotal);
            weeks.add(WeeklyRevenue.builder()
                    .weekNumber(i + 1)
                    .weekStart(releaseDate.plusWeeks(i))
                    .weekEnd(releaseDate.plusWeeks(i + 1).minusDays(1))
                    .domestic(d)
                    .international(ii)
                    .total(wTotal)
                    .theatreCount(theatres[i])
                    .perTheatreAverage(theatres[i] > 0
                            ? d.divide(BigDecimal.valueOf(theatres[i]), 2, RoundingMode.HALF_UP)
                            : BigDecimal.ZERO)
                    .rank(ranks[i])
                    .cumulativeTotal(cumulative)
                    .build());
        }

        BigDecimal breakEven = prod.multiply(BigDecimal.valueOf(2.5));
        boolean profitable = total.compareTo(breakEven) > 0;
        BigDecimal roi = total.subtract(totalBudget)
                .divide(totalBudget, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        return MovieFinancials.builder()
                .movieId(movieId)
                .productionBudget(prod)
                .marketingBudget(mktg)
                .totalBudget(totalBudget)
                .domesticRevenue(dom)
                .internationalRevenue(intl)
                .totalRevenue(total)
                .streamingRevenue(total.multiply(BigDecimal.valueOf(0.15)))
                .merchandisingRevenue(total.multiply(BigDecimal.valueOf(0.08)))
                .weeklyBreakdown(weeks)
                .breakEvenPoint(breakEven)
                .currency("USD")
                .isProfitable(profitable)
                .roi(roi)
                .build();
    }

    public Optional<MovieFinancials> findByMovieId(String movieId) {
        return Optional.ofNullable(store.get(movieId));
    }

    public List<MovieFinancials> findAll() {
        return new ArrayList<>(store.values());
    }
}
