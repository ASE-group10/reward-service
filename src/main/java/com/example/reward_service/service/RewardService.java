package com.example.reward_service.service;

import com.example.reward_service.dao.RewardDao;
import com.example.reward_service.model.Reward;
import com.example.reward_service.model.RewardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardService {

    @Autowired
    private RewardDao rewardDao;

    public Reward validateAndCalculateReward(RewardRequest rewardRequest) {
        Long userId = rewardRequest.getUserId();
        double distance = rewardRequest.getRouteDetails().getDistance();
        boolean isHealthCompliant = rewardRequest.getRouteDetails().isHealthCompliant();

        // Check eligibility based on distance and health-compliance criteria
        boolean isEligible = rewardDao.checkEligibility(userId, distance, isHealthCompliant);

        if (isEligible) {
            // Calculate points (e.g., 10 points per km)
            int points = (int) (distance * 10);
            // Save reward using DAO
            return rewardDao.saveReward(userId, points);
        }
        return new Reward(0, "User is not eligible for a reward.");
    }
}
