package edible.simple;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import edible.simple.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EntityScan(basePackageClasses = {
		SimpleApplication.class,
		Jsr310JpaConverters.class
})
public class SimpleApplication {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(SimpleApplication.class, args);
	}

}
