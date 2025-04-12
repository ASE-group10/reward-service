// File: AppConfigTest.java
package com.example.reward_service.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.*;

class AppConfigTest {

    @Test
    void testRestTemplateBean() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            RestTemplate rt = context.getBean(RestTemplate.class);
            assertNotNull(rt);
        }
    }
}
