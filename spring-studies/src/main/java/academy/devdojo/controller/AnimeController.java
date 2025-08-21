package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import academy.devdojo.mapper.AnimeMapper;
import academy.devdojo.request.anime.AnimePostRequest;
import academy.devdojo.request.anime.AnimePutRequest;
import academy.devdojo.response.anime.AnimeGetResponse;
import academy.devdojo.response.anime.AnimePostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<AnimePostResponse> createAnime(@RequestBody AnimePostRequest request) {
        log.info("Anime '{}'", request.toString());
        var anime = MAPPER.toAnime(request);
        ANIME_LIST.add(anime);
        var response = MAPPER.toAnimePostResponse(anime);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id) {
        log.debug("Delete anime '{}'", id);
        var anime = ANIME_LIST.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime Not Found"));
        ANIME_LIST.remove(anime);
        return ResponseEntity.noContent().build();

    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AnimePutRequest request) {
        log.debug("Request To Update ANime '{}'", request);

        var animeToRemove = ANIME_LIST.stream()
                .filter(a -> a.getId().equals(request.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime Not Found"));

       var animeUpdated =  MAPPER.toAnime(request);

        ANIME_LIST.remove(animeToRemove);

        ANIME_LIST.add(animeUpdated);

        return ResponseEntity.noContent().build();

    }
}
