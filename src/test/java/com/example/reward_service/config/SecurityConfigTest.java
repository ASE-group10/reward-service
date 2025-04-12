// File: SecurityConfigTest.java
package com.example.reward_service.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPublicEndpoints() throws Exception {
        // These endpoints are permitted to all requests.
        mockMvc.perform(get("/calculate-reward")).andExpect(status().isOk());
        mockMvc.perform(get("/total-rewards")).andExpect(status().isOk());
    }
}
