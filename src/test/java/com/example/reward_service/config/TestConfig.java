package com.example.reward_service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {
    // Define a no-op or dummy PyroscopeBean
    @Bean
    @Primary
    public com.example.reward_service.config.PyroscopeBean pyroscopeBean() {
        return new com.example.reward_service.config.PyroscopeBean() {
            @Override
            public void init() {
                // No initialization needed for tests.
            }
        };
    }
}
