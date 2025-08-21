package academy.devdojo.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Anime {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    @Getter
    private static List<Anime> animes = new ArrayList<>();

    static {
        Anime anime1 = Anime.builder().id(1L).name("DBZ").createdAt(LocalDateTime.now()).build();
        Anime anime2 = Anime.builder().id(2L).name("Naruto").createdAt(LocalDateTime.now()).build();
        Anime anime3 = Anime.builder().id(3L).name("Death Note").createdAt(LocalDateTime.now()).build();
        Anime anime4 = Anime.builder().id(4L).name("Yuyu Hakusho").createdAt(LocalDateTime.now()).build();
        animes.addAll(List.of(anime1, anime2, anime3, anime4));
    }

}


