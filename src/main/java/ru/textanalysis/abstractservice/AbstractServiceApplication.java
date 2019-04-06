package ru.textanalysis.abstractservice;

import ru.textanalysis.abstractservice.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class AbstractServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbstractServiceApplication.class, args);
	}
}
