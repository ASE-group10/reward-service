package com.example.reward_service;

import com.example.reward_service.config.PyroscopeBean;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class RewardServiceApplicationTests {

	// This tells Spring to provide a mock instead of initializing PyroscopeBean
	@MockBean
	private PyroscopeBean pyroscopeBean;

	@Test
	void contextLoads() {
		// Test will pass if the application context loads successfully.
	}
}
