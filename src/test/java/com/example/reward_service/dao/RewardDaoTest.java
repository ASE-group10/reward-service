// File: RewardDaoTest.java
package com.example.reward_service.dao;

import com.example.reward_service.entity.RewardEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RewardDaoTest {

    @InjectMocks
    private RewardDao rewardDao;

    @Mock
    private RewardRepository rewardRepository;

    @Test
    void testCheckEligibility() {
        // Eligible if distance >= 1 and health compliant is true.
        assertTrue(rewardDao.checkEligibility("anyUser", 1.0, true));
        assertTrue(rewardDao.checkEligibility("anyUser", 2.0, true));
        // Ineligible cases
        assertFalse(rewardDao.checkEligibility("anyUser", 0.5, true));
        assertFalse(rewardDao.checkEligibility("anyUser", 2.0, false));
    }

    @Test
    void testSaveReward() {
        String userId = "user123";
        int points = 50;
        RewardEntity rewardEntity = new RewardEntity(userId, points);
        rewardEntity.setName("Reward for user " + userId);
        when(rewardRepository.save(any(RewardEntity.class))).thenReturn(rewardEntity);

        RewardEntity saved = rewardDao.saveReward(userId, points);
        assertNotNull(saved);
        assertEquals(userId, saved.getUserId());
        assertEquals(points, saved.getPoints());
        assertEquals("Reward for user " + userId, saved.getName());
    }
}
