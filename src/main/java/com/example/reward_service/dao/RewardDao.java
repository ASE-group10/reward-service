package com.example.reward_service.dao;

import com.example.reward_service.entity.RewardEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RewardDao {

    @Autowired
    private RewardRepository rewardRepository;

    // Simulated eligibility check. Adjust your logic as needed.
    public boolean checkEligibility(String userId, double distance, boolean isHealthCompliant) {
        // For example: eligible if distance is at least 1 km and is health compliant.
        return distance >= 1.0 && isHealthCompliant;
    }

    // Save the reward entity to the database and return it.
    public RewardEntity saveReward(String userId, int points) {
        RewardEntity rewardEntity = new RewardEntity(userId, points);
        rewardEntity.setName("Reward for user " + userId);
        return rewardRepository.save(rewardEntity);
    }
}
