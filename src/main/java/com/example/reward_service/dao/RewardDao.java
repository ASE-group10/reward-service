package com.example.reward_service.dao;

import com.example.reward_service.entity.RewardEntity;
import com.example.reward_service.model.Reward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RewardDao {

    @Autowired
    private RewardRepository rewardRepository;

    /**
     * Eligibility example: user is eligible if the distance is greater than 1 km and
     * the route is health compliant.
     */
    public boolean checkEligibility(Long userId, double distance, boolean isHealthCompliant) {
        return distance > 1.0 && isHealthCompliant;
    }

    /**
     * Save reward in the database and return a Reward DTO.
     */
    public Reward saveReward(Long userId, int points) {
        RewardEntity rewardEntity = new RewardEntity();
        rewardEntity.setUserId(userId);
        rewardEntity.setPoints(points);
        rewardEntity.setName("Reward for user " + userId);
        rewardRepository.save(rewardEntity);
        return new Reward(points, "Reward granted successfully");
    }
}
