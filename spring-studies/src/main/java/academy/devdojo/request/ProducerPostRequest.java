package academy.devdojo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProducerPostRequest
{
    @JsonProperty("full_name")
    private String name;
}
