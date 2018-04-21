package evolution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EvolutionMspUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvolutionMspUserApplication.class, args);
	}
}
