package com.example.reward_service.service;

import org.springframework.stereotype.Service;

@Service
public class MockRouteCalculationService {

    public String getUserModeOfTransport(String userId) {
        // Mock response for development
        return "bus"; // Example response
    }
}
