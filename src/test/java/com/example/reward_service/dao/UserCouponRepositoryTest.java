// File: UserCouponRepositoryTest.java
package com.example.reward_service.dao;

import com.example.reward_service.entity.UserCouponEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserCouponRepositoryTest {

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Test
    void testSaveAndFindByUserIdAndCouponId() {
        UserCouponEntity entity = new UserCouponEntity(null, "userABC", "couponXYZ", false, null);
        userCouponRepository.save(entity);

        Optional<UserCouponEntity> retrieved = userCouponRepository.findByUserIdAndCouponId("userABC", "couponXYZ");
        assertTrue(retrieved.isPresent());
        assertFalse(retrieved.get().isRedeemed());
    }
}
