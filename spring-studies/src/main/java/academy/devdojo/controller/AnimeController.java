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
    public List<Anime> listAll() throws InterruptedException {
        log.info(Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(1);
        return Anime.getAnimes();
    }

    @GetMapping("filter")
    public List<Anime> filterByName(@RequestParam(required = false) String name) {
        var animes = Anime.getAnimes();
        if (animes == null) return animes;

        return animes.stream().filter(a -> a.getName().equalsIgnoreCase(name)).toList();
    }

    @GetMapping("{id}")
    public Anime filterByName(@PathVariable Long id) {
        return Anime.getAnimes().stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
    }

    @PostMapping
    public Anime createAnime(@RequestBody Anime anime) {
        log.info("Anime '{}'", anime.toString());
        var getAnimes = Anime.getAnimes();

        var lastId = getAnimes.getLast().getId();
        anime.setId(lastId + 1);
        getAnimes.add(anime);
        return anime;
    }
}
