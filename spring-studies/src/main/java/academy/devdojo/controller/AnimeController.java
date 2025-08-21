package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import academy.devdojo.mapper.AnimeMapper;
import academy.devdojo.request.anime.AnimePostRequest;
import academy.devdojo.response.anime.AnimeGetResponse;
import academy.devdojo.response.anime.AnimePostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("v1/animes")
@Slf4j
public class AnimeController {

    private static final AnimeMapper MAPPER = AnimeMapper.INSTANCE;
    private static final List<Anime> ANIME_LIST = Anime.getAnimes();

    @GetMapping()
    public ResponseEntity<List<AnimeGetResponse>> listAll(@RequestParam(required = false) String name) {

        var responseList = MAPPER.toAnimeGetResponseList(ANIME_LIST);
        if (name == null) return ResponseEntity.ok(responseList);

        var animeList = responseList.stream().filter(a -> a.getName().equalsIgnoreCase(name)).toList();

        return ResponseEntity.ok(animeList);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> filterByName(@PathVariable Long id) {
        var animeGetResponse = ANIME_LIST.stream().filter(a -> a.getId().equals(id)).findFirst()
                .map(MAPPER::toAnimeGetResponse).orElse(null);
        return ResponseEntity.status(HttpStatus.OK).body(animeGetResponse);
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> createAnime(@RequestBody AnimePostRequest animePostRequest) {
        log.info("Anime '{}'", animePostRequest.toString());
        var anime = MAPPER.toAnime(animePostRequest);
        ANIME_LIST.add(anime);
        var response = MAPPER.toAnimePostResponse(anime);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
