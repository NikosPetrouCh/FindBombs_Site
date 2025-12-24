package gr.aueb.cf.findbombs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FindBombsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FindBombsApplication.class, args);
	}

}


