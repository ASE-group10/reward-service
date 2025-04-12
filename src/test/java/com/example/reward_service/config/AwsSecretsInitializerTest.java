// File: AwsSecretsInitializerTest.java
package com.example.reward_service.config;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.context.ConfigurableApplicationContext;
import static org.mockito.Mockito.*;

class AwsSecretsInitializerTest {

    @Test
    void testInitializeLocalProfile() {
        ConfigurableApplicationContext context = mock(ConfigurableApplicationContext.class);
        ConfigurableEnvironment env = mock(ConfigurableEnvironment.class);
        when(env.getProperty("spring.profiles.active", "default")).thenReturn("local");
        when(context.getEnvironment()).thenReturn(env);

        AwsSecretsInitializer initializer = new AwsSecretsInitializer();
        // In "local" profile, secrets injection is skipped—so no exception is thrown.
        initializer.initialize(context);

        verify(env, times(1)).getProperty("spring.profiles.active", "default");
    }
}
