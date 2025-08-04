package academy.devdojo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@ComponentScan(basePackages = {"outside.devdojo", "academy.devdojo"})
@SpringBootApplication
public class SpringStudiesApplication {

	public static void main(String[] args) {
        var applicationContext = SpringApplication.run(SpringStudiesApplication.class, args);
		Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(System.out::println);
	}

}
