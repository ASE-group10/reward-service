package com.example.reward_service.dao;

import com.example.reward_service.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, String> {
    List<CouponEntity> findByUserId(Long userId);

    // Example custom method if you need to filter by status or expiry:
    List<CouponEntity> findByUserIdAndCouponStatus(Long userId, String couponStatus);
}
