package com.movieverse.financial.service;

import com.movieverse.common.exception.ResourceNotFoundException;
import com.movieverse.financial.model.MovieFinancials;
import com.movieverse.financial.model.WeeklyRevenue;
import com.movieverse.financial.repository.FinancialRepository;
import com.movieverse.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinancialService {

    private final FinancialRepository financialRepository;
    private final MovieService movieService;

    public MovieFinancials getFinancials(String movieId) {
        movieService.validateMovieExists(movieId);
        return financialRepository.findByMovieId(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Financial data", movieId));
    }

    public List<WeeklyRevenue> getWeeklyRevenue(String movieId) {
        return getFinancials(movieId).getWeeklyBreakdown();
    }

    public WeeklyRevenue getWeekByNumber(String movieId, int weekNumber) {
        if (weekNumber < 1 || weekNumber > 4) {
            throw new IllegalArgumentException("Week number must be between 1 and 4");
        }
        return getWeeklyRevenue(movieId).stream()
                .filter(w -> w.getWeekNumber() == weekNumber)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Weekly revenue week-" + weekNumber, movieId));
    }

    public List<MovieFinancials> getAllFinancials() {
        return financialRepository.findAll();
    }
}
