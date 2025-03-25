package com.example.reward_service.dao;

import com.example.reward_service.entity.RewardEntity;
import com.example.reward_service.model.Reward;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RewardDaoTest {

    private RewardDao rewardDao;

    @BeforeEach
    public void setup() {
        rewardDao = new RewardDao();  // Initialize RewardDao
    }

    @Test
    public void testCheckEligibility() {
        // Test when the distance is greater than 1 km and health-compliant
        boolean eligibility = rewardDao.checkEligibility("fdfdsfs", 2.5, true);
        assertTrue(eligibility, "User should be eligible when distance > 1 km and health-compliant.");

        // Test when the distance is less than or equal to 1 km
        eligibility = rewardDao.checkEligibility("dsdasd", 0.5, true);
        assertTrue(!eligibility, "User should not be eligible when distance <= 1 km.");

        // Test when health-compliant is false
        eligibility = rewardDao.checkEligibility("dsdadwwe", 2.5, false);
        assertTrue(!eligibility, "User should not be eligible when not health-compliant.");
    }

    @Test
    public void testSaveReward() {
        // Simulate saving reward and check if reward points are calculated correctly
        RewardEntity reward = rewardDao.saveReward("dasd2w2asd", 25);  // User 123 with 25 points

        // Check if the reward object is created with correct points and status
        assertEquals(25, reward.getPoints(), "Points should be 25.");
        assertEquals("Reward saved successfully.", reward.getStatus(), "Status should be 'Reward saved successfully.'");
    }
}
