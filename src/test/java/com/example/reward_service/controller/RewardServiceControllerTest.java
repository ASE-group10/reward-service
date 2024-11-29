package com.example.reward_service.controller;

import com.example.reward_service.model.Reward;
import com.example.reward_service.model.RewardRequest;
import com.example.reward_service.model.RouteDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RewardServiceControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;  // Used to perform HTTP requests

    private RewardRequest rewardRequest;

    @BeforeEach
    public void setup() {
        rewardRequest = new RewardRequest();
        rewardRequest.setUserId(123L);  // Set userId as Long

        // Set up RouteDetails with distance and health compliance
        RouteDetails routeDetails = new RouteDetails();
        routeDetails.setDistance(2.5);  // Set distance to 2.5 km for testing
        routeDetails.setHealthCompliant(true);  // Set health compliance to true for testing

        rewardRequest.setRouteDetails(routeDetails);  // Set RouteDetails in RewardRequest
    }

    @Test
    public void testValidateReward() {
        // The endpoint to test
        String url = "/validate-reward";

        // Create the HttpEntity with the RewardRequest as body
        HttpEntity<RewardRequest> entity = new HttpEntity<>(rewardRequest);

        // Call the endpoint and capture the response
        ResponseEntity<Reward> response = restTemplate.exchange(url, HttpMethod.POST, entity, Reward.class);

        // Assert the response status and the Reward data
        assertEquals(200, response.getStatusCodeValue());  // Check HTTP status code
        Reward reward = response.getBody();
        assert reward != null;
        assertEquals(25, reward.getPoints());  // 2.5 km * 10 points/km = 25 points
        assertEquals("Reward saved successfully.", reward.getStatus());  // Update to match the actual response message
    }

    @Test
    public void testGetHelloData() {
        // Perform GET request for the /hello-reward endpoint
        ResponseEntity<String> response = restTemplate.getForEntity("/hello-reward", String.class);

        // Assert the response status and body
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Hello from Reward Service!", response.getBody());  // Ensure the correct message is returned
    }
}
