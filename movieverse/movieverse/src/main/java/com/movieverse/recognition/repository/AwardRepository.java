package com.movieverse.recognition.repository;

import com.movieverse.recognition.model.Award;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class AwardRepository {

    private final Map<String, List<Award>> byMovie = new ConcurrentHashMap<>();

    @PostConstruct
    public void seed() {

        // THE DARK KNIGHT (2008) - 8 Oscar noms, 2 wins
        addAwards("MOV-001", List.of(
            win("AWD-001-1", "Academy Awards", 2009, "Best Supporting Actor", "Heath Ledger", "Academy of Motion Picture Arts and Sciences", 81),
            win("AWD-001-2", "Academy Awards", 2009, "Best Film Editing", "Lee Smith", "Academy of Motion Picture Arts and Sciences", 81),
            nom("AWD-001-3", "Academy Awards", 2009, "Best Cinematography", "Wally Pfister", "Academy of Motion Picture Arts and Sciences", 81),
            nom("AWD-001-4", "Academy Awards", 2009, "Best Art Direction", "Nathan Crowley", "Academy of Motion Picture Arts and Sciences", 81),
            nom("AWD-001-5", "Academy Awards", 2009, "Best Sound Mixing", "The Dark Knight", "Academy of Motion Picture Arts and Sciences", 81),
            nom("AWD-001-6", "Academy Awards", 2009, "Best Sound Editing", "The Dark Knight", "Academy of Motion Picture Arts and Sciences", 81),
            nom("AWD-001-7", "Academy Awards", 2009, "Best Makeup", "The Dark Knight", "Academy of Motion Picture Arts and Sciences", 81),
            win("AWD-001-8", "BAFTA Awards", 2009, "Best Supporting Actor", "Heath Ledger", "BAFTA", 62),
            nom("AWD-001-9", "BAFTA Awards", 2009, "Best Cinematography", "Wally Pfister", "BAFTA", 62),
            nom("AWD-001-10", "Saturn Awards", 2009, "Best Science Fiction Film", "The Dark Knight", "Academy of Science Fiction", 35)
        ));

        // INCEPTION (2010) - 8 Oscar noms, 4 wins
        addAwards("MOV-002", List.of(
            win("AWD-002-1", "Academy Awards", 2011, "Best Cinematography", "Wally Pfister", "Academy of Motion Picture Arts and Sciences", 83),
            win("AWD-002-2", "Academy Awards", 2011, "Best Visual Effects", "Inception VFX Team", "Academy of Motion Picture Arts and Sciences", 83),
            win("AWD-002-3", "Academy Awards", 2011, "Best Sound Mixing", "Inception Sound Team", "Academy of Motion Picture Arts and Sciences", 83),
            win("AWD-002-4", "Academy Awards", 2011, "Best Sound Editing", "Inception Sound Team", "Academy of Motion Picture Arts and Sciences", 83),
            nom("AWD-002-5", "Academy Awards", 2011, "Best Picture", "Inception", "Academy of Motion Picture Arts and Sciences", 83),
            nom("AWD-002-6", "Academy Awards", 2011, "Best Director", "Christopher Nolan", "Academy of Motion Picture Arts and Sciences", 83),
            nom("AWD-002-7", "Academy Awards", 2011, "Best Original Screenplay", "Christopher Nolan", "Academy of Motion Picture Arts and Sciences", 83),
            nom("AWD-002-8", "Academy Awards", 2011, "Best Original Score", "Hans Zimmer", "Academy of Motion Picture Arts and Sciences", 83),
            win("AWD-002-9", "BAFTA Awards", 2011, "Best Special Visual Effects", "Inception VFX Team", "BAFTA", 64),
            nom("AWD-002-10", "Golden Globes", 2011, "Best Motion Picture – Drama", "Inception", "Hollywood Foreign Press Association", 68)
        ));

        // PARASITE (2019) - 6 Oscar noms, 4 wins (first non-English film to win Best Picture)
        addAwards("MOV-003", List.of(
            win("AWD-003-1", "Academy Awards", 2020, "Best Picture", "Parasite", "Academy of Motion Picture Arts and Sciences", 92),
            win("AWD-003-2", "Academy Awards", 2020, "Best Director", "Bong Joon-ho", "Academy of Motion Picture Arts and Sciences", 92),
            win("AWD-003-3", "Academy Awards", 2020, "Best International Feature Film", "Parasite", "Academy of Motion Picture Arts and Sciences", 92),
            win("AWD-003-4", "Academy Awards", 2020, "Best Original Screenplay", "Bong Joon-ho, Han Jin-won", "Academy of Motion Picture Arts and Sciences", 92),
            nom("AWD-003-5", "Academy Awards", 2020, "Best Film Editing", "Yang Jin-mo", "Academy of Motion Picture Arts and Sciences", 92),
            nom("AWD-003-6", "Academy Awards", 2020, "Best Production Design", "Lee Ha-jun", "Academy of Motion Picture Arts and Sciences", 92),
            win("AWD-003-7", "Palme d'Or", 2019, "Palme d'Or", "Parasite", "Cannes Film Festival", 72),
            win("AWD-003-8", "Golden Globes", 2020, "Best Foreign Language Film", "Parasite", "Hollywood Foreign Press Association", 77),
            win("AWD-003-9", "SAG Awards", 2020, "Outstanding Performance by a Cast", "Parasite Cast", "Screen Actors Guild", 26),
            win("AWD-003-10", "BAFTA Awards", 2020, "Best Film Not in the English Language", "Parasite", "BAFTA", 73)
        ));

        // AVENGERS ENDGAME (2019)
        addAwards("MOV-004", List.of(
            nom("AWD-004-1", "Academy Awards", 2020, "Best Visual Effects", "Avengers VFX Team", "Academy of Motion Picture Arts and Sciences", 92),
            win("AWD-004-2", "MTV Movie Awards", 2019, "Best Movie", "Avengers: Endgame", "MTV", 2019),
            win("AWD-004-3", "People's Choice Awards", 2019, "The Movie of 2019", "Avengers: Endgame", "NBC", 2019),
            win("AWD-004-4", "Saturn Awards", 2020, "Best Superhero Film", "Avengers: Endgame", "Academy of Science Fiction", 46),
            nom("AWD-004-5", "Golden Globes", 2020, "Best Motion Picture – Drama", "Avengers: Endgame", "Hollywood Foreign Press Association", 77)
        ));

        // SHAWSHANK REDEMPTION (1994) - 7 Oscar noms, 0 wins (lost to Forrest Gump)
        addAwards("MOV-005", List.of(
            nom("AWD-005-1", "Academy Awards", 1995, "Best Picture", "The Shawshank Redemption", "Academy of Motion Picture Arts and Sciences", 67),
            nom("AWD-005-2", "Academy Awards", 1995, "Best Actor", "Morgan Freeman", "Academy of Motion Picture Arts and Sciences", 67),
            nom("AWD-005-3", "Academy Awards", 1995, "Best Adapted Screenplay", "Frank Darabont", "Academy of Motion Picture Arts and Sciences", 67),
            nom("AWD-005-4", "Academy Awards", 1995, "Best Cinematography", "Roger Deakins", "Academy of Motion Picture Arts and Sciences", 67),
            nom("AWD-005-5", "Academy Awards", 1995, "Best Film Editing", "Richard Francis-Bruce", "Academy of Motion Picture Arts and Sciences", 67),
            nom("AWD-005-6", "Academy Awards", 1995, "Best Original Score", "Thomas Newman", "Academy of Motion Picture Arts and Sciences", 67),
            nom("AWD-005-7", "Academy Awards", 1995, "Best Sound", "The Shawshank Redemption", "Academy of Motion Picture Arts and Sciences", 67),
            win("AWD-005-8", "Saturn Awards", 1995, "Best Writing", "Frank Darabont", "Academy of Science Fiction", 21)
        ));

        // THE GODFATHER (1972) - 11 Oscar noms, 3 wins
        addAwards("MOV-007", List.of(
            win("AWD-007-1", "Academy Awards", 1973, "Best Picture", "The Godfather", "Academy of Motion Picture Arts and Sciences", 45),
            win("AWD-007-2", "Academy Awards", 1973, "Best Actor", "Marlon Brando", "Academy of Motion Picture Arts and Sciences", 45),
            win("AWD-007-3", "Academy Awards", 1973, "Best Adapted Screenplay", "Mario Puzo, Francis Ford Coppola", "Academy of Motion Picture Arts and Sciences", 45),
            nom("AWD-007-4", "Academy Awards", 1973, "Best Supporting Actor", "James Caan", "Academy of Motion Picture Arts and Sciences", 45),
            nom("AWD-007-5", "Academy Awards", 1973, "Best Supporting Actor", "Al Pacino", "Academy of Motion Picture Arts and Sciences", 45),
            nom("AWD-007-6", "Academy Awards", 1973, "Best Supporting Actor", "Robert Duvall", "Academy of Motion Picture Arts and Sciences", 45),
            nom("AWD-007-7", "Academy Awards", 1973, "Best Director", "Francis Ford Coppola", "Academy of Motion Picture Arts and Sciences", 45),
            win("AWD-007-8", "Golden Globes", 1973, "Best Motion Picture – Drama", "The Godfather", "Hollywood Foreign Press Association", 30),
            win("AWD-007-9", "DGA Award", 1973, "Outstanding Directorial Achievement", "Francis Ford Coppola", "Directors Guild of America", 25)
        ));

        // EVERYTHING EVERYWHERE (2022) - 11 Oscar noms, 7 wins
        addAwards("MOV-008", List.of(
            win("AWD-008-1", "Academy Awards", 2023, "Best Picture", "Everything Everywhere All at Once", "Academy of Motion Picture Arts and Sciences", 95),
            win("AWD-008-2", "Academy Awards", 2023, "Best Director", "Daniel Kwan, Daniel Scheinert", "Academy of Motion Picture Arts and Sciences", 95),
            win("AWD-008-3", "Academy Awards", 2023, "Best Actress", "Michelle Yeoh", "Academy of Motion Picture Arts and Sciences", 95),
            win("AWD-008-4", "Academy Awards", 2023, "Best Supporting Actor", "Ke Huy Quan", "Academy of Motion Picture Arts and Sciences", 95),
            win("AWD-008-5", "Academy Awards", 2023, "Best Supporting Actress", "Jamie Lee Curtis", "Academy of Motion Picture Arts and Sciences", 95),
            win("AWD-008-6", "Academy Awards", 2023, "Best Original Screenplay", "Daniel Kwan, Daniel Scheinert", "Academy of Motion Picture Arts and Sciences", 95),
            win("AWD-008-7", "Academy Awards", 2023, "Best Film Editing", "Paul Rogers", "Academy of Motion Picture Arts and Sciences", 95),
            nom("AWD-008-8", "Academy Awards", 2023, "Best Costume Design", "Shirley Kurata", "Academy of Motion Picture Arts and Sciences", 95),
            nom("AWD-008-9", "Academy Awards", 2023, "Best Original Score", "Son Lux", "Academy of Motion Picture Arts and Sciences", 95),
            win("AWD-008-10", "SAG Awards", 2023, "Outstanding Performance by a Cast", "EEAAO Cast", "Screen Actors Guild", 29),
            win("AWD-008-11", "Golden Globes", 2023, "Best Motion Picture – Musical or Comedy", "Everything Everywhere All at Once", "Hollywood Foreign Press Association", 80)
        ));

        // OPPENHEIMER (2023) - 13 Oscar noms, 7 wins
        addAwards("MOV-009", List.of(
            win("AWD-009-1", "Academy Awards", 2024, "Best Picture", "Oppenheimer", "Academy of Motion Picture Arts and Sciences", 96),
            win("AWD-009-2", "Academy Awards", 2024, "Best Director", "Christopher Nolan", "Academy of Motion Picture Arts and Sciences", 96),
            win("AWD-009-3", "Academy Awards", 2024, "Best Actor", "Cillian Murphy", "Academy of Motion Picture Arts and Sciences", 96),
            win("AWD-009-4", "Academy Awards", 2024, "Best Supporting Actor", "Robert Downey Jr.", "Academy of Motion Picture Arts and Sciences", 96),
            win("AWD-009-5", "Academy Awards", 2024, "Best Film Editing", "Jennifer Lame", "Academy of Motion Picture Arts and Sciences", 96),
            win("AWD-009-6", "Academy Awards", 2024, "Best Cinematography", "Hoyte van Hoytema", "Academy of Motion Picture Arts and Sciences", 96),
            win("AWD-009-7", "Academy Awards", 2024, "Best Original Score", "Ludwig Göransson", "Academy of Motion Picture Arts and Sciences", 96),
            nom("AWD-009-8", "Academy Awards", 2024, "Best Supporting Actress", "Emily Blunt", "Academy of Motion Picture Arts and Sciences", 96),
            nom("AWD-009-9", "Academy Awards", 2024, "Best Adapted Screenplay", "Christopher Nolan", "Academy of Motion Picture Arts and Sciences", 96),
            nom("AWD-009-10", "Academy Awards", 2024, "Best Costume Design", "Ellen Mirojnick", "Academy of Motion Picture Arts and Sciences", 96),
            win("AWD-009-11", "Golden Globes", 2024, "Best Motion Picture – Drama", "Oppenheimer", "Hollywood Foreign Press Association", 81),
            win("AWD-009-12", "BAFTA Awards", 2024, "Best Film", "Oppenheimer", "BAFTA", 77),
            win("AWD-009-13", "DGA Award", 2024, "Outstanding Directorial Achievement", "Christopher Nolan", "Directors Guild of America", 76)
        ));

        // PULP FICTION (1994) - 7 Oscar noms, 1 win
        addAwards("MOV-010", List.of(
            win("AWD-010-1", "Academy Awards", 1995, "Best Original Screenplay", "Quentin Tarantino, Roger Avary", "Academy of Motion Picture Arts and Sciences", 67),
            nom("AWD-010-2", "Academy Awards", 1995, "Best Picture", "Pulp Fiction", "Academy of Motion Picture Arts and Sciences", 67),
            nom("AWD-010-3", "Academy Awards", 1995, "Best Director", "Quentin Tarantino", "Academy of Motion Picture Arts and Sciences", 67),
            nom("AWD-010-4", "Academy Awards", 1995, "Best Actor", "John Travolta", "Academy of Motion Picture Arts and Sciences", 67),
            nom("AWD-010-5", "Academy Awards", 1995, "Best Supporting Actor", "Samuel L. Jackson", "Academy of Motion Picture Arts and Sciences", 67),
            nom("AWD-010-6", "Academy Awards", 1995, "Best Supporting Actress", "Uma Thurman", "Academy of Motion Picture Arts and Sciences", 67),
            nom("AWD-010-7", "Academy Awards", 1995, "Best Film Editing", "Sally Menke", "Academy of Motion Picture Arts and Sciences", 67),
            win("AWD-010-8", "Palme d'Or", 1994, "Palme d'Or", "Pulp Fiction", "Cannes Film Festival", 47)
        ));

        log.info("AwardRepository seeded with awards for {} movies", byMovie.size());
    }

    private Award win(String id, String ceremony, int year, String category,
                      String nominee, String presentedBy, int edition) {
        return Award.builder().id(id).ceremonyName(ceremony).ceremonyYear(year)
                .category(category).nominee(nominee).won(true)
                .presentedBy(presentedBy).ceremonyEdition(edition).build();
    }

    private Award nom(String id, String ceremony, int year, String category,
                      String nominee, String presentedBy, int edition) {
        return Award.builder().id(id).ceremonyName(ceremony).ceremonyYear(year)
                .category(category).nominee(nominee).won(false)
                .presentedBy(presentedBy).ceremonyEdition(edition).build();
    }

    private void addAwards(String movieId, List<Award> awards) {
        List<Award> tagged = awards.stream()
                .map(a -> { a.setMovieId(movieId); return a; })
                .collect(Collectors.toList());
        byMovie.put(movieId, tagged);
    }

    public List<Award> findByMovieId(String movieId) {
        return byMovie.getOrDefault(movieId, Collections.emptyList());
    }

    public List<Award> findAllMovieAwards() {
        return byMovie.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
