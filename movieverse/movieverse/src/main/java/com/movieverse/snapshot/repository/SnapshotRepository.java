package com.movieverse.snapshot.repository;

import com.movieverse.snapshot.model.Snapshot;
import com.movieverse.snapshot.model.SnapshotCategory;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class SnapshotRepository {

    private final Map<String, List<Snapshot>> byMovie = new ConcurrentHashMap<>();

    @PostConstruct
    public void seed() {
        addSnapshots("MOV-001", List.of(
            s("SNP-001-1", "The Pencil Trick", "The Joker makes a pencil disappear in the most terrifying way", "00:06:20", "Why so serious?", "Mob meeting at a restaurant", SnapshotCategory.CHARACTER_INTRODUCTION, true, 1),
            s("SNP-001-2", "Batman vs Joker Interrogation", "Batman beats the Joker in the interrogation room as the clock ticks", "01:14:00", "You have nothing! Nothing to threaten me with!", "Gotham Police interrogation room", SnapshotCategory.CLIMAX, true, 2),
            s("SNP-001-3", "The Truck Flip", "The Joker flips an entire 18-wheeler with a cable", "01:32:10", null, "Gotham streets during prisoner transport", SnapshotCategory.ACTION_SEQUENCE, true, 3),
            s("SNP-001-4", "Harvey Dent's Transformation", "Harvey Dent wakes up in the hospital to discover his face", "01:52:30", "The Joker chose me!", "Gotham General Hospital", SnapshotCategory.PLOT_TWIST, true, 4),
            s("SNP-001-5", "Why So Serious Nurse Scene", "The Joker in nurse's clothing blows up the hospital", "01:55:00", "Why so serious?", "Gotham General Hospital exterior", SnapshotCategory.ICONIC_DIALOGUE, true, 5)
        ));

        addSnapshots("MOV-002", List.of(
            s("SNP-002-1", "Paris Folding", "Ariadne folds Paris upon itself in a jaw-dropping demonstration", "00:36:45", "What's happening?", "Paris café streets", SnapshotCategory.VISUAL_SPECTACLE, true, 1),
            s("SNP-002-2", "Hallway Fight", "Arthur fights guards in a rotating hallway with zero gravity", "01:43:00", null, "Hotel corridor, dream level 2", SnapshotCategory.ACTION_SEQUENCE, true, 2),
            s("SNP-002-3", "The Kick - Van Chase", "The van falls off a bridge in ultra slow motion as kicks synchronize", "02:00:00", null, "Bridge over river", SnapshotCategory.CLIMAX, true, 3),
            s("SNP-002-4", "The Spinning Top Ending", "Cobb spins his totem and reunites with his children — does it fall?", "02:17:30", null, "Cobb's home", SnapshotCategory.FINAL_SCENE, true, 4),
            s("SNP-002-5", "Mal's Death", "Mal steps off the window ledge, believing it is a dream", "00:58:00", "You said we'd be together.", "Hotel room", SnapshotCategory.EMOTIONAL_MOMENT, true, 5)
        ));

        addSnapshots("MOV-003", List.of(
            s("SNP-003-1", "The Staircase Revelation", "The Kims discover the secret bunker beneath the Park house", "01:04:00", null, "Park residence basement", SnapshotCategory.PLOT_TWIST, true, 1),
            s("SNP-003-2", "The Flood", "The Kim family's semi-basement floods during a torrential rainstorm", "01:22:00", null, "Kim family's semi-basement home", SnapshotCategory.EMOTIONAL_MOMENT, true, 2),
            s("SNP-003-3", "The Garden Party Massacre", "Everything erupts into chaos at the Park family garden party", "01:45:00", null, "Park family garden", SnapshotCategory.CLIMAX, true, 3),
            s("SNP-003-4", "The Stone", "Ki-woo attempts to drown Ki-jung with the Scholar's Rock", "01:52:00", null, "Park family home", SnapshotCategory.ACTION_SEQUENCE, false, 4),
            s("SNP-003-5", "Ki-taek's Letter", "Ki-woo reads his father's coded letter in the final scene", "02:05:00", "I'm going to buy that house.", "Semi-basement", SnapshotCategory.FINAL_SCENE, true, 5)
        ));

        addSnapshots("MOV-004", List.of(
            s("SNP-004-1", "Portals Scene", "Every Marvel hero steps through glowing portals for the final battle", "02:30:00", "Avengers... Assemble.", "Ruined Avengers compound", SnapshotCategory.CLIMAX, true, 1),
            s("SNP-004-2", "Tony's Snap", "Tony Stark snaps the Infinity Gauntlet and wipes out Thanos's army", "02:45:00", "I am Iron Man.", "Battlefield", SnapshotCategory.FINAL_SCENE, true, 2),
            s("SNP-004-3", "Time Heist Planning", "The Avengers devise the quantum realm time travel plan in the compound", "00:58:00", "Part of the journey is the end.", "Avengers compound", SnapshotCategory.CHARACTER_INTRODUCTION, false, 3),
            s("SNP-004-4", "Peggy and Steve's Dance", "Old Steve Rogers returns the stones and stays to live his life", "02:58:00", null, "1940s living room", SnapshotCategory.EMOTIONAL_MOMENT, true, 4),
            s("SNP-004-5", "Cap Lifts Mjolnir", "Steve Rogers proves himself worthy and picks up Thor's hammer", "02:38:00", "I knew it!", "Ruined compound", SnapshotCategory.ICONIC_DIALOGUE, true, 5)
        ));

        addSnapshots("MOV-005", List.of(
            s("SNP-005-1", "Andy Escapes Through the Sewage Pipe", "Andy crawls 500 yards through sewage to freedom in the rain", "02:00:00", "Andy Dufresne, who crawled through a river of shit and came out clean on the other side.", "Shawshank prison drainage pipe", SnapshotCategory.CLIMAX, true, 1),
            s("SNP-005-2", "Mozart Through the Loudspeakers", "Andy locks himself in the warden's office and plays opera over the PA", "01:02:00", null, "Shawshank Prison yard", SnapshotCategory.ICONIC_DIALOGUE, true, 2),
            s("SNP-005-3", "Red's Parole Speech", "Red finally gives an honest parole speech after years of rejection", "01:55:00", "I have to remind myself that some birds aren't meant to be caged.", "Parole board room", SnapshotCategory.EMOTIONAL_MOMENT, true, 3),
            s("SNP-005-4", "Zihuatanejo Reunion", "Andy and Red reunite on the beach in Mexico", "02:18:00", null, "Zihuatanejo beach, Mexico", SnapshotCategory.FINAL_SCENE, true, 4)
        ));

        addSnapshots("MOV-009", List.of(
            s("SNP-009-1", "The Trinity Test", "The first atomic bomb detonation lights up the New Mexico sky", "01:52:00", "Now I am become Death, the destroyer of worlds.", "Trinity test site, New Mexico", SnapshotCategory.CLIMAX, true, 1),
            s("SNP-009-2", "The Hearing", "Oppenheimer sits alone under brutal cross-examination by Strauss", "02:15:00", null, "AEC hearing room", SnapshotCategory.EMOTIONAL_MOMENT, true, 2),
            s("SNP-009-3", "Los Alamos Assembly", "The team gathers in the desert for the first time", "00:45:00", null, "Los Alamos, New Mexico", SnapshotCategory.CHARACTER_INTRODUCTION, false, 3)
        ));

        log.info("SnapshotRepository seeded with snapshots for {} movies", byMovie.size());
    }

    private Snapshot s(String id, String title, String desc, String ts,
                       String quote, String context, SnapshotCategory cat,
                       boolean iconic, int rank) {
        return Snapshot.builder()
                .id(id)
                .title(title)
                .description(desc)
                .timestampInFilm(ts)
                .quote(quote)
                .sceneContext(context)
                .category(cat)
                .isIconic(iconic)
                .popularityRank(rank)
                .build();
    }

    private void addSnapshots(String movieId, List<Snapshot> snaps) {
        List<Snapshot> tagged = snaps.stream()
                .map(s -> { s.setMovieId(movieId); return s; })
                .collect(java.util.stream.Collectors.toList());
        byMovie.put(movieId, tagged);
    }

    public List<Snapshot> findByMovieId(String movieId) {
        return byMovie.getOrDefault(movieId, Collections.emptyList());
    }

    public Optional<Snapshot> findById(String movieId, String snapshotId) {
        return byMovie.getOrDefault(movieId, Collections.emptyList())
                .stream().filter(s -> s.getId().equals(snapshotId)).findFirst();
    }

    public List<Snapshot> findByCategory(String movieId, SnapshotCategory category) {
        return findByMovieId(movieId).stream()
                .filter(s -> s.getCategory() == category)
                .collect(Collectors.toList());
    }
}
