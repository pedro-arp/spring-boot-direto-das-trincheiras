package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    headers = "x-api-key=123")
    public Producer createProducer(@RequestBody Producer producer, @RequestHeader HttpHeaders headers) {
        log.info("headers '{}'", headers);
        var getProducers = Producer.getProducers();

        var lastId = getProducers.getLast().getId();
        producer.setId(lastId + 1);
        getProducers.add(producer);
        return producer;
    }
}
