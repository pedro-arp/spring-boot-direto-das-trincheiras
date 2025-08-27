package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import academy.devdojo.mapper.ProducerMapper;
import academy.devdojo.request.producer.ProducerPostRequest;
import academy.devdojo.request.producer.ProducerPutRequest;
import academy.devdojo.response.producer.ProducerGetResponse;
import academy.devdojo.response.producer.ProducerPostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {

    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;

    @GetMapping()
    public ResponseEntity<List<ProducerGetResponse>> listAll() {
        var producerGetResponseList = MAPPER.toProducerGetResponseList(Producer.getProducers());
        return ResponseEntity.ok(producerGetResponseList);
    }

    @GetMapping("filter")
    public ResponseEntity<List<Producer>> filterByName(@RequestParam(required = false) String name) {
        var producers = Producer.getProducers();
        if (producers == null) return ResponseEntity.ok(Collections.emptyList());

        var listProducer = producers.stream().filter(a -> a.getName().equalsIgnoreCase(name)).toList();
        MAPPER.toProducerGetResponseList(listProducer);
        return ResponseEntity.ok(listProducer);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProducerGetResponse> filterByName(@PathVariable Long id) {
        var producerGetResponse = Producer.getProducers().stream().filter(a -> a.getId().equals(id)).findFirst().map(MAPPER::toProducerGetResponse).orElse(null);
        return ResponseEntity.ok(producerGetResponse);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "x-api-key")
    public ResponseEntity<ProducerPostResponse> createProducer(@RequestBody ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers) {
        log.info("headers '{}'", headers);

        Producer producer = MAPPER.toProducer(producerPostRequest);
        var producerPostResponse = MAPPER.toProducerPostResponse(producer);
        Producer.getProducers().add(producer);

        return ResponseEntity.status(HttpStatus.CREATED).body(producerPostResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProducer(@PathVariable Long id) {
        log.debug("Deleting producer '{}'", id);
        var producer = Producer.getProducers()
                .stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer Not Found"));
        Producer.getProducers().remove(producer);
        return ResponseEntity.noContent().build();

    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest producerPutRequest){
        var producerToRemove = Producer.getProducers()
                .stream()
                .filter(a -> a.getId().equals(producerPutRequest.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer Not Found"));
        Producer.getProducers().remove(producerToRemove);
        var producerUpdated = MAPPER.toProducer(producerPutRequest, producerToRemove.getCreatedAt());
        Producer.getProducers().add(producerUpdated);
        return ResponseEntity.noContent().build();
    }

}
