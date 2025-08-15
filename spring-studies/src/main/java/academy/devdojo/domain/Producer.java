package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Producer {
    private Long id;
    @JsonProperty("full_name")
    private String name;
    @Getter
    private static List<Producer> producers = new ArrayList<>();
static {
    Producer producer1 = new Producer(1L, "Mappa");
    Producer producer2 = new Producer(2L, "Kyoto Animation");
    Producer producer3 = new Producer(3L, "Madhouse");
    Producer producer4 = new Producer(4L, "Studio Ghibli");
    producers.addAll(List.of(producer1, producer2, producer3, producer4));
}

}


