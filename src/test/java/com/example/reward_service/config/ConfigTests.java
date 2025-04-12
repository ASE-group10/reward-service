package com.example.reward_service.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.reward_service.config.AppConfig;
import com.example.reward_service.config.AwsSecretsInitializer;
import com.example.reward_service.config.PyroscopeBean;
import com.example.reward_service.config.SecurityConfig;
import io.pyroscope.javaagent.PyroscopeAgent;
import io.pyroscope.javaagent.config.Config;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

class ConfigTests {

    // === AppConfig Tests ===

    @Test
    void testRestTemplateBean() {
        AppConfig config = new AppConfig();
        assertNotNull(config.restTemplate(), "Expected a non-null RestTemplate bean");
    }

    // === SecurityConfig Tests ===

    @Test
    void testSecurityFilterChainBean() {
        // Create an application context containing only the SecurityConfig class.
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SecurityConfig.class)) {
            SecurityFilterChain filterChain = context.getBean("filterChain", SecurityFilterChain.class);
            assertNotNull(filterChain, "SecurityFilterChain bean should be created");
        }
    }

    // === AwsSecretsInitializer Tests ===

    @Test
    void testAwsSecretsInitializerSkipsWhenLocalProfile() {
        // Create mocks for the context and environment.
        ConfigurableApplicationContext context = mock(ConfigurableApplicationContext.class);
        ConfigurableEnvironment env = mock(ConfigurableEnvironment.class);
        when(env.getProperty("spring.profiles.active", "default")).thenReturn("local");
        when(context.getEnvironment()).thenReturn(env);

        AwsSecretsInitializer initializer = new AwsSecretsInitializer();
        // For local profile, no AWS secrets injection should occur.
        assertDoesNotThrow(() -> initializer.initialize(context));
        verify(env, times(1)).getProperty("spring.profiles.active", "default");
    }

    // === PyroscopeBean Tests ===
    @Test
    void testPyroscopeBeanInit() {
        PyroscopeBean bean = new PyroscopeBean();
        // Set required fields using ReflectionTestUtils
        ReflectionTestUtils.setField(bean, "activeProfile", "prod");
        ReflectionTestUtils.setField(bean, "applicationName", "TestApp");
        ReflectionTestUtils.setField(bean, "pyroscopeServerAddress", "http://localhost:4040");
        ReflectionTestUtils.setField(bean, "pyroscopeServerAuthUser", "user");
        ReflectionTestUtils.setField(bean, "pyroscopeServerAuthPassword", "pass");

        // Use static mocking to intercept the PyroscopeAgent.start() call.
        try (MockedStatic<PyroscopeAgent> mockedStatic = mockStatic(PyroscopeAgent.class)) {
            bean.init();
            // Disambiguate by casting the matcher to Config.
            mockedStatic.verify(() -> PyroscopeAgent.start((Config) any()), times(1));
        }
    }
}
