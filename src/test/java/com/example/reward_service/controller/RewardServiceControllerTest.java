package com.example.reward_service.controller;

import com.example.reward_service.entity.RewardEntity;
import com.example.reward_service.model.RewardRequest;
import com.example.reward_service.model.RouteDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RewardServiceControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;
    private RewardRequest validRequest;
    private RewardRequest invalidRequest;

    @BeforeEach
    void setUp() {
        // Setup test headers with mock token
        headers = new HttpHeaders();
        headers.set("Authorization", "Bearer mock-token-123");

        // Valid request setup
        validRequest = new RewardRequest();
        RewardRequest.RouteDetails validRoute = new RewardRequest.RouteDetails();
        validRoute.setDistance(5.0);
        validRoute.setHealthCompliant(true);
        validRequest.setRouteDetails(validRoute);

        // Invalid request setup
        invalidRequest = new RewardRequest();
        RewardRequest.RouteDetails invalidRoute = new RewardRequest.RouteDetails();
        invalidRoute.setDistance(0.5);
        invalidRoute.setHealthCompliant(false);
        invalidRequest.setRouteDetails(invalidRoute);
    }

    @Test
    void calculateReward_ValidRequest_ReturnsReward() {
        HttpEntity<RewardRequest> entity = new HttpEntity<>(validRequest, headers);

        ResponseEntity<RewardEntity> response = restTemplate.exchange(
                "/calculate-reward",
                HttpMethod.POST,
                entity,
                RewardEntity.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(50, response.getBody().getPoints()); // 5.0 km * 10 points/km
    }

    @Test
    void calculateReward_InvalidRequest_ReturnsZeroPoints() {
        HttpEntity<RewardRequest> entity = new HttpEntity<>(invalidRequest, headers);

        ResponseEntity<RewardEntity> response = restTemplate.exchange(
                "/calculate-reward",
                HttpMethod.POST,
                entity,
                RewardEntity.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getPoints());
    }

    @Test
    void getRewardsHistory_ValidUser_ReturnsList() {
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<RewardEntity[]> response = restTemplate.exchange(
                "/rewards-history",
                HttpMethod.GET,
                entity,
                RewardEntity[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void redeemCoupon_ValidRequest_ReturnsSuccess() {
        HttpEntity<String> entity = new HttpEntity<>(
                "{\"couponId\":\"COUPON-123\"}",
                headers
        );

        ResponseEntity<String> response = restTemplate.exchange(
                "/coupons/redeem",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("redeemed successfully"));
    }
}