package academy.devdojo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class Anime {
    private Long id;
    private String name;
    @Getter
    private static List<Anime> animes = new ArrayList<>();

    static {
        Anime anime1 = new Anime(1L, "DBZ");
        Anime anime2 = new Anime(2L, "Naruto");
        Anime anime3 = new Anime(3L, "Death Note");
        Anime anime4 = new Anime(4L, "Yuyu Hakusho");
        animes.addAll(List.of(anime1, anime2, anime3, anime4));
    }

}


