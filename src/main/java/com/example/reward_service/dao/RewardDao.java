package com.example.reward_service.dao;

import com.example.reward_service.entity.RewardEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.reward_service.dao.RewardRepository;

@Component
public class RewardDao {

    @Autowired
    private RewardRepository rewardRepository;

    public void saveReward(String userId, int points) {
        RewardEntity rewardEntity = new RewardEntity();
        rewardEntity.setUserId(userId);
        rewardEntity.setRewardPoints(points);
        rewardRepository.save(rewardEntity);
    }
}
