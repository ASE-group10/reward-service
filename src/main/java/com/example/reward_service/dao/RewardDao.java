package com.example.reward_service.dao;

import com.example.reward_service.model.Reward;
import org.springframework.stereotype.Repository;

@Repository
public class RewardDao {

    public boolean checkEligibility(Long userId, double distance, boolean isHealthCompliant) {
        // Example: Eligible if distance > 1 km and route is health-compliant
        return distance > 1.0 && isHealthCompliant;
    }

    public Reward saveReward(Long userId, int points) {
        // Simulating saving reward details in the database
        System.out.println("Saving reward for user ID: " + userId + " with points: " + points);
        // Now we are using the constructor that accepts (int, String)
        return new Reward(points, "Reward saved successfully.");
    }
}
