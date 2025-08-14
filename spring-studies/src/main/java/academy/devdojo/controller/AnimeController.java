package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("v1/animes")
@Slf4j
public class AnimeController {
    @GetMapping()
    public List<String> listAll() throws InterruptedException {
        log.info(Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(1);
        return List.of("DBZ", "Naruto", "One Piece", "Yuyu Hakusho");
    }

    @GetMapping("filter")
    public List<Anime> filterByName(@RequestParam(required = false) String name) {
        var animes = Anime.animeList();
        if(animes == null) return animes;

        return animes.stream().filter(a -> a.getName().equalsIgnoreCase(name)).toList();
    }
    @GetMapping("{id}")
    public Anime filterByName(@PathVariable Long id) {
        return Anime.animeList().stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
    }
}
