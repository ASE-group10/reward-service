package com.example.reward_service.dao;

import com.example.reward_service.model.Reward;
import org.springframework.stereotype.Repository;

@Repository
public class RewardDao {
    public boolean checkEligibility(Long userId, double distance, boolean isHealthCompliant) {
        return distance > 1.0 && isHealthCompliant;
    }

    public Reward saveReward(Long userId, int points) {
        return new Reward(points, "Reward saved successfully.");
    }
}
