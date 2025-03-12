package com.example.reward_service.service;

import com.example.reward_service.dao.RewardRepository;
import com.example.reward_service.entity.RewardEntity;
import com.example.reward_service.model.Reward;
import com.example.reward_service.model.RewardRequest;
import com.example.reward_service.model.RouteDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RewardService {

    private final RewardRepository rewardRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public RewardService(RewardRepository rewardRepository, RestTemplate restTemplate) {
        this.rewardRepository = rewardRepository;
        this.restTemplate = restTemplate;
    }

    public Reward validateAndCalculateReward(RewardRequest rewardRequest) {
        try {
            if (rewardRequest.getUserId() == null || rewardRequest.getTransportMode() == null) {
                return new Reward(0, "Invalid request: Missing userId or transportMode.");
            }

            RouteDetails routeDetails = rewardRequest.getRouteDetails();
            if (routeDetails == null || !routeDetails.isValidDistance()) {
                return new Reward(0, "Invalid route details: Distance must be positive.");
            }

            // Debugging print statements before calling calculatePoints()
            System.out.println("DEBUG: Validating reward request...");
            System.out.println("UserId: " + rewardRequest.getUserId());
            System.out.println("Transport Mode: " + rewardRequest.getTransportMode());
            System.out.println("Distance: " + routeDetails.getDistance());
            System.out.println("Health Compliant: " + routeDetails.isHealthCompliant());

            // Calculate points
            int points = calculatePoints(rewardRequest.getTransportMode(), routeDetails.getDistance(), routeDetails.isHealthCompliant());

            if (points == 0) {
                return new Reward(0, "Invalid transport mode.");
            }

            // Save reward to database
            RewardEntity rewardEntity = new RewardEntity();
            rewardEntity.setUserId(rewardRequest.getUserId());
            rewardEntity.setTransportMode(rewardRequest.getTransportMode());
            rewardEntity.setPoints(points);
            rewardRepository.save(rewardEntity);

            // Register reward with external service
            registerRewardInUserProfile(rewardEntity);

            return new Reward(points, "Reward successfully saved and registered.");
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return new Reward(0, "Internal Server Error.");
        }
    }

    private int calculatePoints(String transportMode, double distance, boolean healthCompliant) {
        int basePoints = 0;
        switch (transportMode.toLowerCase()) {
            case "car":
                basePoints = 2;
                break;
            case "bike":
                basePoints = 4;
                break;
            case "bus":
                basePoints = 8;
                break;
            case "walk":
                basePoints = 10;
                break;
            default:
                return 0;
        }

        int bonus = 0;
        if (distance > 5) {
            bonus += 2;
            System.out.println("Bonus for distance > 5: +2");
        }
        if (healthCompliant) {
            bonus += 3;
            System.out.println("Bonus for healthCompliant: +3");
        }

        int totalPoints = basePoints + bonus;
        System.out.println("Final Calculation: Transport Mode: " + transportMode + ", Base Points: " + basePoints +
                ", Distance: " + distance + ", Bonus: " + bonus + ", Total Points: " + totalPoints);

        return totalPoints;
    }

    private void registerRewardInUserProfile(RewardEntity rewardEntity) {
        String userProfileUrl = "http://user-profile-service/rewards/register"; // Replace with actual URL

        try {
            restTemplate.postForEntity(userProfileUrl, rewardEntity, Void.class);
            System.out.println("Successfully registered reward in user profile service.");
        } catch (Exception e) {
            System.out.println("Failed to register reward in user profile service.");
        }
    }
}
