package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import academy.devdojo.request.ProducerPostRequest;
import academy.devdojo.response.ProducerGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {
    @GetMapping()
    public List<Producer> listAll() throws InterruptedException {
        log.info(Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(1);
        return Producer.getProducers();
    }

    @GetMapping("filter")
    public List<Producer> filterByName(@RequestParam(required = false) String name) {
        var producers = Producer.getProducers();
        if (producers == null) return Collections.emptyList();

        return producers.stream().filter(a -> a.getName().equalsIgnoreCase(name)).toList();
    }

    @GetMapping("{id}")
    public Producer filterByName(@PathVariable Long id) {
        return Producer.getProducers().stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "x-api-key")
    public ResponseEntity<ProducerGetResponse> createProducer(@RequestBody ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers) {
        log.info("headers '{}'", headers);

        var producer = Producer.builder().id(Producer.getProducers().getLast().getId() + 1)
                .name(producerPostRequest.getName())
                .createdAt(LocalDateTime.now())
                .build();

        Producer.getProducers().add(producer);

        var response = ProducerGetResponse.builder().id(producer.getId()).name(producer.getName()).createdAt(producer.getCreatedAt()).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
