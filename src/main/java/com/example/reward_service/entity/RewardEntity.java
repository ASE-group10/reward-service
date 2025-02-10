package com.example.reward_service.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RewardEntity {

    @Id
    private String userId;
    private int rewardPoints;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public int getRewardPoints() { return rewardPoints; }
    public void setRewardPoints(int rewardPoints) { this.rewardPoints = rewardPoints; }
}
