package spring.hibernate.learning.LearningApp;

import org.springframework.boot.SpringApplication;

public class TestLearningAppApplication {

	public static void main(String[] args) {
		SpringApplication.from(LearningAppApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
