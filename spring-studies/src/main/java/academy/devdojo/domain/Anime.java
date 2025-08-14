package academy.devdojo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Anime {
    private Long id;
    private String name;

    public static List<Anime> animeList() {
        Anime anime1 = new Anime(1L, "DBZ");
        Anime anime2 = new Anime(2L, "Naruto");
        Anime anime3 = new Anime(3L, "Death Note");
        Anime anime4 = new Anime(4L, "Yuyu Hakusho");
        return List.of(anime1, anime2, anime3, anime4);
    }
}


