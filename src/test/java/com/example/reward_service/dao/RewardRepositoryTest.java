// File: RewardRepositoryTest.java
package com.example.reward_service.dao;

import com.example.reward_service.entity.RewardEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RewardRepositoryTest {

    @Autowired
    private RewardRepository rewardRepository;

    @Test
    void testSaveAndFindByUserId() {
        RewardEntity reward = new RewardEntity("user456", 50);
        reward.setName("Test Reward");
        rewardRepository.save(reward);

        List<RewardEntity> rewards = rewardRepository.findByUserId("user456");
        assertFalse(rewards.isEmpty());
        assertEquals("Test Reward", rewards.get(0).getName());
    }
}
