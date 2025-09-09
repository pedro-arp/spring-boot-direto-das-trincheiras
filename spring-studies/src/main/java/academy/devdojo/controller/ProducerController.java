package academy.devdojo.controller;

import academy.devdojo.mapper.ProducerMapper;
import academy.devdojo.request.producer.ProducerPostRequest;
import academy.devdojo.request.producer.ProducerPutRequest;
import academy.devdojo.response.producer.ProducerGetResponse;
import academy.devdojo.response.producer.ProducerPostResponse;
import academy.devdojo.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {

    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;
    private final ProducerService service;

    public ProducerController() {
        this.service = new ProducerService();
    }


    @GetMapping()
    public ResponseEntity<List<ProducerGetResponse>> listAll(@RequestParam(required = false) String name) {
        var producers = service.findAll(name);
        var producerGetResponses = MAPPER.toProducerGetResponseList(producers);
        return ResponseEntity.ok(producerGetResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProducerGetResponse> filterByName(@PathVariable Long id) {
        var producer = service.findByIdOrThrowNotFound(id);
        var producerGetResponse = MAPPER.toProducerGetResponse(producer);
        return ResponseEntity.ok(producerGetResponse);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "x-api-key")
    public ResponseEntity<ProducerPostResponse> createProducer(@RequestBody ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers) {
        log.info("headers '{}'", headers);

        var producer = MAPPER.toProducer(producerPostRequest);
        var producerSaved = service.save(producer);
        var producerPostResponse = MAPPER.toProducerPostResponse(producerSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(producerPostResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProducer(@PathVariable Long id) {
        log.debug("Deleting producer '{}'", id);
        service.delete(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest producerPutRequest) {
        var producerUpdated = MAPPER.toProducer(producerPutRequest);
        service.update(producerUpdated);
        return ResponseEntity.noContent().build();
    }

}
