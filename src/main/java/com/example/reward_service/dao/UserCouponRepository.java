package com.example.reward_service.dao;

import com.example.reward_service.entity.UserCouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCouponEntity, Long> {

    // Find a bridging record for a given user + coupon
    Optional<UserCouponEntity> findByUserIdAndCouponId(String userId, String couponId);

    // Find all bridging records for a user that are redeemed = true
    List<UserCouponEntity> findByUserIdAndRedeemed(String userId, boolean redeemed);
}
