package com.movieverse.movie.repository;

import com.movieverse.movie.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class MovieRepository {

    private final Map<String, Movie> store = new ConcurrentHashMap<>();

    @PostConstruct
    public void seed() {
        List<Movie> movies = List.of(

            Movie.builder()
                .id("MOV-001")
                .title("The Dark Knight")
                .originalTitle("The Dark Knight")
                .year(2008)
                .releaseDate(LocalDate.of(2008, 7, 18))
                .genre(Genre.ACTION)
                .additionalGenres(List.of(Genre.CRIME, Genre.DRAMA))
                .synopsis("When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.")
                .runtimeMinutes(152)
                .language("English")
                .country("USA/UK")
                .studio("Syncopy")
                .distributor("Warner Bros.")
                .director("Christopher Nolan")
                .writers(List.of("Jonathan Nolan", "Christopher Nolan"))
                .producers(List.of("Charles Roven", "Emma Thomas", "Christopher Nolan"))
                .cast(List.of(
                    CastMember.builder().actorName("Christian Bale").characterName("Bruce Wayne / Batman").isLead(true).billingOrder(1).build(),
                    CastMember.builder().actorName("Heath Ledger").characterName("The Joker").isLead(true).billingOrder(2).build(),
                    CastMember.builder().actorName("Aaron Eckhart").characterName("Harvey Dent / Two-Face").isLead(false).billingOrder(3).build(),
                    CastMember.builder().actorName("Michael Caine").characterName("Alfred Pennyworth").isLead(false).billingOrder(4).build(),
                    CastMember.builder().actorName("Maggie Gyllenhaal").characterName("Rachel Dawes").isLead(false).billingOrder(5).build(),
                    CastMember.builder().actorName("Gary Oldman").characterName("Commissioner Gordon").isLead(false).billingOrder(6).build(),
                    CastMember.builder().actorName("Morgan Freeman").characterName("Lucius Fox").isLead(false).billingOrder(7).build()
                ))
                .crew(List.of(
                    CrewMember.builder().name("Wally Pfister").role("Cinematographer").department("Camera").build(),
                    CrewMember.builder().name("Lee Smith").role("Editor").department("Post-Production").build(),
                    CrewMember.builder().name("Hans Zimmer").role("Composer").department("Music").build(),
                    CrewMember.builder().name("Nathan Crowley").role("Production Designer").department("Art").build()
                ))
                .mpaaRating("PG-13")
                .status(MovieStatus.STREAMING)
                .build(),

            Movie.builder()
                .id("MOV-002")
                .title("Inception")
                .year(2010)
                .releaseDate(LocalDate.of(2010, 7, 16))
                .genre(Genre.SCI_FI)
                .additionalGenres(List.of(Genre.ACTION, Genre.THRILLER))
                .synopsis("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.")
                .runtimeMinutes(148)
                .language("English")
                .country("USA/UK")
                .studio("Syncopy")
                .distributor("Warner Bros.")
                .director("Christopher Nolan")
                .writers(List.of("Christopher Nolan"))
                .producers(List.of("Christopher Nolan", "Emma Thomas"))
                .cast(List.of(
                    CastMember.builder().actorName("Leonardo DiCaprio").characterName("Dominic Cobb").isLead(true).billingOrder(1).build(),
                    CastMember.builder().actorName("Joseph Gordon-Levitt").characterName("Arthur").isLead(true).billingOrder(2).build(),
                    CastMember.builder().actorName("Elliot Page").characterName("Ariadne").isLead(false).billingOrder(3).build(),
                    CastMember.builder().actorName("Tom Hardy").characterName("Eames").isLead(false).billingOrder(4).build(),
                    CastMember.builder().actorName("Ken Watanabe").characterName("Saito").isLead(false).billingOrder(5).build(),
                    CastMember.builder().actorName("Marion Cotillard").characterName("Mal Cobb").isLead(false).billingOrder(6).build()
                ))
                .crew(List.of(
                    CrewMember.builder().name("Wally Pfister").role("Cinematographer").department("Camera").build(),
                    CrewMember.builder().name("Lee Smith").role("Editor").department("Post-Production").build(),
                    CrewMember.builder().name("Hans Zimmer").role("Composer").department("Music").build()
                ))
                .mpaaRating("PG-13")
                .status(MovieStatus.STREAMING)
                .build(),

            Movie.builder()
                .id("MOV-003")
                .title("Parasite")
                .year(2019)
                .releaseDate(LocalDate.of(2019, 10, 11))
                .genre(Genre.THRILLER)
                .additionalGenres(List.of(Genre.DRAMA, Genre.COMEDY))
                .synopsis("Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan.")
                .runtimeMinutes(132)
                .language("Korean")
                .country("South Korea")
                .studio("Barunson E&A")
                .distributor("CJ Entertainment / Neon")
                .director("Bong Joon-ho")
                .writers(List.of("Bong Joon-ho", "Han Jin-won"))
                .producers(List.of("Kwak Sin-ae", "Moon Yang-kwon"))
                .cast(List.of(
                    CastMember.builder().actorName("Song Kang-ho").characterName("Kim Ki-taek").isLead(true).billingOrder(1).build(),
                    CastMember.builder().actorName("Lee Sun-kyun").characterName("Park Dong-ik").isLead(true).billingOrder(2).build(),
                    CastMember.builder().actorName("Cho Yeo-jeong").characterName("Choi Yeon-gyo").isLead(false).billingOrder(3).build(),
                    CastMember.builder().actorName("Choi Woo-shik").characterName("Kim Ki-woo").isLead(false).billingOrder(4).build(),
                    CastMember.builder().actorName("Park So-dam").characterName("Kim Ki-jung").isLead(false).billingOrder(5).build()
                ))
                .crew(List.of(
                    CrewMember.builder().name("Hong Kyung-pyo").role("Cinematographer").department("Camera").build(),
                    CrewMember.builder().name("Yang Jin-mo").role("Editor").department("Post-Production").build(),
                    CrewMember.builder().name("Jung Jae-il").role("Composer").department("Music").build()
                ))
                .mpaaRating("R")
                .status(MovieStatus.STREAMING)
                .build(),

            Movie.builder()
                .id("MOV-004")
                .title("Avengers: Endgame")
                .year(2019)
                .releaseDate(LocalDate.of(2019, 4, 26))
                .genre(Genre.ACTION)
                .additionalGenres(List.of(Genre.ADVENTURE, Genre.SCI_FI))
                .synopsis("After the devastating events of Infinity War, the universe is in ruins. The Avengers assemble once more to reverse Thanos's actions and restore balance.")
                .runtimeMinutes(181)
                .language("English")
                .country("USA")
                .studio("Marvel Studios")
                .distributor("Walt Disney Pictures")
                .director("Anthony Russo, Joe Russo")
                .writers(List.of("Christopher Markus", "Stephen McFeely"))
                .producers(List.of("Kevin Feige"))
                .cast(List.of(
                    CastMember.builder().actorName("Robert Downey Jr.").characterName("Tony Stark / Iron Man").isLead(true).billingOrder(1).build(),
                    CastMember.builder().actorName("Chris Evans").characterName("Steve Rogers / Captain America").isLead(true).billingOrder(2).build(),
                    CastMember.builder().actorName("Mark Ruffalo").characterName("Bruce Banner / Hulk").isLead(false).billingOrder(3).build(),
                    CastMember.builder().actorName("Chris Hemsworth").characterName("Thor").isLead(false).billingOrder(4).build(),
                    CastMember.builder().actorName("Scarlett Johansson").characterName("Natasha Romanoff / Black Widow").isLead(false).billingOrder(5).build(),
                    CastMember.builder().actorName("Jeremy Renner").characterName("Clint Barton / Hawkeye").isLead(false).billingOrder(6).build(),
                    CastMember.builder().actorName("Josh Brolin").characterName("Thanos").isLead(false).billingOrder(7).build()
                ))
                .crew(List.of(
                    CrewMember.builder().name("Trent Opaloch").role("Cinematographer").department("Camera").build(),
                    CrewMember.builder().name("Jeffrey Ford").role("Editor").department("Post-Production").build(),
                    CrewMember.builder().name("Alan Silvestri").role("Composer").department("Music").build()
                ))
                .mpaaRating("PG-13")
                .status(MovieStatus.STREAMING)
                .build(),

            Movie.builder()
                .id("MOV-005")
                .title("The Shawshank Redemption")
                .year(1994)
                .releaseDate(LocalDate.of(1994, 9, 23))
                .genre(Genre.DRAMA)
                .additionalGenres(List.of(Genre.CRIME))
                .synopsis("Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.")
                .runtimeMinutes(142)
                .language("English")
                .country("USA")
                .studio("Castle Rock Entertainment")
                .distributor("Columbia Pictures")
                .director("Frank Darabont")
                .writers(List.of("Frank Darabont"))
                .producers(List.of("Niki Marvin"))
                .cast(List.of(
                    CastMember.builder().actorName("Tim Robbins").characterName("Andy Dufresne").isLead(true).billingOrder(1).build(),
                    CastMember.builder().actorName("Morgan Freeman").characterName("Ellis Boyd 'Red' Redding").isLead(true).billingOrder(2).build(),
                    CastMember.builder().actorName("Bob Gunton").characterName("Warden Norton").isLead(false).billingOrder(3).build(),
                    CastMember.builder().actorName("William Sadler").characterName("Heywood").isLead(false).billingOrder(4).build()
                ))
                .crew(List.of(
                    CrewMember.builder().name("Roger Deakins").role("Cinematographer").department("Camera").build(),
                    CrewMember.builder().name("Richard Francis-Bruce").role("Editor").department("Post-Production").build(),
                    CrewMember.builder().name("Thomas Newman").role("Composer").department("Music").build()
                ))
                .mpaaRating("R")
                .status(MovieStatus.STREAMING)
                .build(),

            Movie.builder()
                .id("MOV-006")
                .title("Interstellar")
                .year(2014)
                .releaseDate(LocalDate.of(2014, 11, 7))
                .genre(Genre.SCI_FI)
                .additionalGenres(List.of(Genre.ADVENTURE, Genre.DRAMA))
                .synopsis("A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.")
                .runtimeMinutes(169)
                .language("English")
                .country("USA/UK/Canada")
                .studio("Syncopy / Lynda Obst Productions")
                .distributor("Paramount Pictures / Warner Bros.")
                .director("Christopher Nolan")
                .writers(List.of("Jonathan Nolan", "Christopher Nolan"))
                .producers(List.of("Christopher Nolan", "Emma Thomas", "Lynda Obst"))
                .cast(List.of(
                    CastMember.builder().actorName("Matthew McConaughey").characterName("Cooper").isLead(true).billingOrder(1).build(),
                    CastMember.builder().actorName("Anne Hathaway").characterName("Brand").isLead(true).billingOrder(2).build(),
                    CastMember.builder().actorName("Jessica Chastain").characterName("Murph (adult)").isLead(false).billingOrder(3).build(),
                    CastMember.builder().actorName("Michael Caine").characterName("Professor Brand").isLead(false).billingOrder(4).build(),
                    CastMember.builder().actorName("Matt Damon").characterName("Dr. Mann").isLead(false).billingOrder(5).build()
                ))
                .crew(List.of(
                    CrewMember.builder().name("Hoyte van Hoytema").role("Cinematographer").department("Camera").build(),
                    CrewMember.builder().name("Lee Smith").role("Editor").department("Post-Production").build(),
                    CrewMember.builder().name("Hans Zimmer").role("Composer").department("Music").build()
                ))
                .mpaaRating("PG-13")
                .status(MovieStatus.STREAMING)
                .build(),

            Movie.builder()
                .id("MOV-007")
                .title("The Godfather")
                .year(1972)
                .releaseDate(LocalDate.of(1972, 3, 24))
                .genre(Genre.CRIME)
                .additionalGenres(List.of(Genre.DRAMA))
                .synopsis("The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.")
                .runtimeMinutes(175)
                .language("English")
                .country("USA")
                .studio("Paramount Pictures")
                .distributor("Paramount Pictures")
                .director("Francis Ford Coppola")
                .writers(List.of("Mario Puzo", "Francis Ford Coppola"))
                .producers(List.of("Albert S. Ruddy"))
                .cast(List.of(
                    CastMember.builder().actorName("Marlon Brando").characterName("Don Vito Corleone").isLead(true).billingOrder(1).build(),
                    CastMember.builder().actorName("Al Pacino").characterName("Michael Corleone").isLead(true).billingOrder(2).build(),
                    CastMember.builder().actorName("James Caan").characterName("Sonny Corleone").isLead(false).billingOrder(3).build(),
                    CastMember.builder().actorName("Robert Duvall").characterName("Tom Hagen").isLead(false).billingOrder(4).build(),
                    CastMember.builder().actorName("Diane Keaton").characterName("Kay Adams").isLead(false).billingOrder(5).build()
                ))
                .crew(List.of(
                    CrewMember.builder().name("Gordon Willis").role("Cinematographer").department("Camera").build(),
                    CrewMember.builder().name("William Reynolds").role("Editor").department("Post-Production").build(),
                    CrewMember.builder().name("Nino Rota").role("Composer").department("Music").build()
                ))
                .mpaaRating("R")
                .status(MovieStatus.STREAMING)
                .build(),

            Movie.builder()
                .id("MOV-008")
                .title("Everything Everywhere All at Once")
                .year(2022)
                .releaseDate(LocalDate.of(2022, 3, 25))
                .genre(Genre.SCI_FI)
                .additionalGenres(List.of(Genre.COMEDY, Genre.ACTION))
                .synopsis("An aging Chinese immigrant is swept up in an insane adventure, where she alone can save what's important to her by connecting with the lives she could have led in other universes.")
                .runtimeMinutes(139)
                .language("English/Mandarin/Cantonese")
                .country("USA")
                .studio("A24 / AGBO")
                .distributor("A24")
                .director("Daniel Kwan, Daniel Scheinert")
                .writers(List.of("Daniel Kwan", "Daniel Scheinert"))
                .producers(List.of("Daniel Kwan", "Daniel Scheinert", "Jonathan Wang"))
                .cast(List.of(
                    CastMember.builder().actorName("Michelle Yeoh").characterName("Evelyn Wang").isLead(true).billingOrder(1).build(),
                    CastMember.builder().actorName("Stephanie Hsu").characterName("Joy Wang / Jobu Tupaki").isLead(true).billingOrder(2).build(),
                    CastMember.builder().actorName("Ke Huy Quan").characterName("Waymond Wang").isLead(true).billingOrder(3).build(),
                    CastMember.builder().actorName("Jamie Lee Curtis").characterName("Deirdre Beaubeirdre").isLead(false).billingOrder(4).build(),
                    CastMember.builder().actorName("James Hong").characterName("Gong Gong").isLead(false).billingOrder(5).build()
                ))
                .crew(List.of(
                    CrewMember.builder().name("Larkin Seiple").role("Cinematographer").department("Camera").build(),
                    CrewMember.builder().name("Paul Rogers").role("Editor").department("Post-Production").build(),
                    CrewMember.builder().name("Son Lux").role("Composer").department("Music").build()
                ))
                .mpaaRating("R")
                .status(MovieStatus.STREAMING)
                .build(),

            Movie.builder()
                .id("MOV-009")
                .title("Oppenheimer")
                .year(2023)
                .releaseDate(LocalDate.of(2023, 7, 21))
                .genre(Genre.BIOGRAPHY)
                .additionalGenres(List.of(Genre.DRAMA, Genre.HISTORY))
                .synopsis("The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb during World War II.")
                .runtimeMinutes(180)
                .language("English")
                .country("USA/UK")
                .studio("Universal Pictures / Syncopy")
                .distributor("Universal Pictures")
                .director("Christopher Nolan")
                .writers(List.of("Christopher Nolan"))
                .producers(List.of("Christopher Nolan", "Emma Thomas", "Charles Roven"))
                .cast(List.of(
                    CastMember.builder().actorName("Cillian Murphy").characterName("J. Robert Oppenheimer").isLead(true).billingOrder(1).build(),
                    CastMember.builder().actorName("Emily Blunt").characterName("Katherine 'Kitty' Oppenheimer").isLead(false).billingOrder(2).build(),
                    CastMember.builder().actorName("Matt Damon").characterName("General Leslie Groves Jr.").isLead(false).billingOrder(3).build(),
                    CastMember.builder().actorName("Robert Downey Jr.").characterName("Lewis Strauss").isLead(false).billingOrder(4).build(),
                    CastMember.builder().actorName("Florence Pugh").characterName("Jean Tatlock").isLead(false).billingOrder(5).build(),
                    CastMember.builder().actorName("Josh Hartnett").characterName("Ernest Lawrence").isLead(false).billingOrder(6).build()
                ))
                .crew(List.of(
                    CrewMember.builder().name("Hoyte van Hoytema").role("Cinematographer").department("Camera").build(),
                    CrewMember.builder().name("Jennifer Lame").role("Editor").department("Post-Production").build(),
                    CrewMember.builder().name("Ludwig Göransson").role("Composer").department("Music").build()
                ))
                .mpaaRating("R")
                .status(MovieStatus.RELEASED)
                .build(),

            Movie.builder()
                .id("MOV-010")
                .title("Pulp Fiction")
                .year(1994)
                .releaseDate(LocalDate.of(1994, 10, 14))
                .genre(Genre.CRIME)
                .additionalGenres(List.of(Genre.DRAMA, Genre.THRILLER))
                .synopsis("The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.")
                .runtimeMinutes(154)
                .language("English")
                .country("USA")
                .studio("A Band Apart / Jersey Films")
                .distributor("Miramax Films")
                .director("Quentin Tarantino")
                .writers(List.of("Quentin Tarantino", "Roger Avary"))
                .producers(List.of("Lawrence Bender"))
                .cast(List.of(
                    CastMember.builder().actorName("John Travolta").characterName("Vincent Vega").isLead(true).billingOrder(1).build(),
                    CastMember.builder().actorName("Samuel L. Jackson").characterName("Jules Winnfield").isLead(true).billingOrder(2).build(),
                    CastMember.builder().actorName("Uma Thurman").characterName("Mia Wallace").isLead(false).billingOrder(3).build(),
                    CastMember.builder().actorName("Bruce Willis").characterName("Butch Coolidge").isLead(false).billingOrder(4).build(),
                    CastMember.builder().actorName("Harvey Keitel").characterName("The Wolf").isLead(false).billingOrder(5).build()
                ))
                .crew(List.of(
                    CrewMember.builder().name("Andrzej Sekula").role("Cinematographer").department("Camera").build(),
                    CrewMember.builder().name("Sally Menke").role("Editor").department("Post-Production").build()
                ))
                .mpaaRating("R")
                .status(MovieStatus.STREAMING)
                .build()
        );

        movies.forEach(m -> store.put(m.getId(), m));
        log.info("MovieRepository seeded with {} movies", store.size());
    }

    public List<Movie> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Movie> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Movie> findByYear(int year) {
        return store.values().stream()
                .filter(m -> m.getYear() == year)
                .collect(Collectors.toList());
    }

    public List<Movie> findByGenre(Genre genre) {
        return store.values().stream()
                .filter(m -> m.getGenre() == genre ||
                             m.getAdditionalGenres().contains(genre))
                .collect(Collectors.toList());
    }

    public List<Movie> findByDirector(String director) {
        return store.values().stream()
                .filter(m -> m.getDirector().toLowerCase().contains(director.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Movie> findByActor(String actorName) {
        return store.values().stream()
                .filter(m -> m.getCast().stream()
                        .anyMatch(c -> c.getActorName().toLowerCase().contains(actorName.toLowerCase())))
                .collect(Collectors.toList());
    }

    public boolean exists(String id) {
        return store.containsKey(id);
    }
}
