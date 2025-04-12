// File: TotalRewardsRepositoryTest.java
package com.example.reward_service.dao;

import com.example.reward_service.entity.TotalRewardsEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

@DataJpaTest
class TotalRewardsRepositoryTest {

    @Autowired
    private TotalRewardsRepository totalRewardsRepository;

    @Test
    void testSaveAndFind() {
        TotalRewardsEntity entity = new TotalRewardsEntity("user789", 100);
        totalRewardsRepository.save(entity);

        Optional<TotalRewardsEntity> retrieved = totalRewardsRepository.findById("user789");
        assertTrue(retrieved.isPresent());
        assertEquals(100, retrieved.get().getTotalPoints());
    }
}
