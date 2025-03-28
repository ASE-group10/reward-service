package com.example.reward_service;

import com.example.reward_service.config.AwsSecretsInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class RewardServiceApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(RewardServiceApplication.class);
		app.addInitializers(new AwsSecretsInitializer());
		app.run(args);
	}

}
