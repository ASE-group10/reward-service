package com.example.reward_service.controller;

import com.example.reward_service.model.GeometryCoordinates;
import com.example.reward_service.model.Reward;
import com.example.reward_service.model.RewardRequest;
import com.example.reward_service.model.RouteDetails;
import com.example.reward_service.service.RewardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class RewardServiceController {

    private RewardService rewardService;

    @Autowired
    public RewardServiceController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @PostMapping("/validate-reward")
    public Reward validateReward(@RequestBody RewardRequest rewardRequest) {
        // Validate and calculate the reward
        return rewardService.validateAndCalculateReward(rewardRequest);
    }
    @GetMapping("/routes")
    public ResponseEntity<List<RouteDetails>> getAllRoutes() {
        return ResponseEntity.ok(rewardService.getAllRoutes());
    }
    @GetMapping("/fetch-coordinates")
    public ResponseEntity<List<GeometryCoordinates>> fetchCoordinates() {
        try {
            List<GeometryCoordinates> coordinates = rewardService.fetchCoordinatesFromEnvironmentalDataService();
            return ResponseEntity.ok(coordinates);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/hello-reward")
    public String getHelloData() {
        // Simple endpoint for testing
        return "Hello from Reward Service!";
    }
}
