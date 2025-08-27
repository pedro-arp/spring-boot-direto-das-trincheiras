package academy.devdojo.mapper;

import academy.devdojo.domain.Producer;
import academy.devdojo.request.producer.ProducerPostRequest;
import academy.devdojo.request.producer.ProducerPutRequest;
import academy.devdojo.response.producer.ProducerGetResponse;
import academy.devdojo.response.producer.ProducerPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ProducerMapper {
    ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);


    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(Producer.getProducers().getLast().getId() + 1)")
    Producer toProducer(ProducerPostRequest postRequest);

    Producer toProducer(ProducerPutRequest request, LocalDateTime createdAt);

    ProducerPostResponse toProducerPostResponse(Producer producer);

    ProducerGetResponse toProducerGetResponse(Producer producer);

    List<ProducerGetResponse> toProducerGetResponseList(List<Producer> producers);
}
