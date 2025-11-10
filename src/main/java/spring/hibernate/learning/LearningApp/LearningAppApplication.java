package spring.hibernate.learning.LearningApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class LearningAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningAppApplication.class, args);
	}

}
