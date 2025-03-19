package com.example.reward_service.service;

import com.example.reward_service.dao.CouponRepository;
import com.example.reward_service.dao.RewardDao;
import com.example.reward_service.dao.RewardRepository;
import com.example.reward_service.entity.CouponEntity;
import com.example.reward_service.entity.RewardEntity;
import com.example.reward_service.model.Coupon;
import com.example.reward_service.model.Reward;
import com.example.reward_service.model.RewardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RewardService {

    @Autowired
    private RewardDao rewardDao;

    @Autowired
    private RewardRepository rewardRepository;  // For reward persistence

    @Autowired
    private CouponRepository couponRepository;  // For coupon persistence

    @Autowired
    private RestTemplate restTemplate;

    // -------------------------------------------------------------------------
    // 1. validateAndCalculateReward
    // -------------------------------------------------------------------------
    public Reward validateAndCalculateReward(RewardRequest rewardRequest) {
        Long userId = rewardRequest.getUserId();
        double distance = rewardRequest.getRouteDetails().getDistance();
        boolean isHealthCompliant = rewardRequest.getRouteDetails().isHealthCompliant();

        // Check eligibility via DAO (custom logic)
        boolean isEligible = rewardDao.checkEligibility(userId, distance, isHealthCompliant);

        if (isEligible) {
            // Calculate points (for example, 10 points per km)
            int points = (int) (distance * 10);
            // Save reward and return a Reward DTO
            return rewardDao.saveReward(userId, points);
        }
        return new Reward(0, "User is not eligible for a reward.");
    }

    // -------------------------------------------------------------------------
    // 2. getRewardHistory
    // -------------------------------------------------------------------------
    public List<RewardEntity> getRewardHistory(Long userId) {
        return rewardRepository.findByUserId(userId);
    }

    // -------------------------------------------------------------------------
    // 3. getCoupons
    // -------------------------------------------------------------------------
    public List<Coupon> getCoupons(Long userId) {
        List<CouponEntity> couponEntities = couponRepository.findByUserId(userId);
        List<Coupon> coupons = new ArrayList<>();
        for (CouponEntity entity : couponEntities) {
            coupons.add(new Coupon(entity.getCouponId(), entity.getDescription(), entity.isValid(), entity.isRedeemed()));
        }
        return coupons;
    }

    // -------------------------------------------------------------------------
    // 4. redeemCoupon
    // -------------------------------------------------------------------------
    public Coupon redeemCoupon(Long userId, String couponId) {
        Optional<CouponEntity> couponOpt = couponRepository.findById(couponId);
        if (!couponOpt.isPresent()) {
            throw new RuntimeException("Coupon not found");
        }
        CouponEntity couponEntity = couponOpt.get();
        // Verify coupon ownership
        if (!couponEntity.getUserId().equals(userId)) {
            throw new RuntimeException("Coupon does not belong to the user");
        }
        // Ensure the coupon has not already been redeemed
        if (couponEntity.isRedeemed()) {
            throw new RuntimeException("Coupon already redeemed");
        }
        // Redeem the coupon: mark it as redeemed and invalidate it
        couponEntity.setRedeemed(true);
        couponEntity.setValid(false);
        couponRepository.save(couponEntity);
        return new Coupon(couponEntity.getCouponId(), couponEntity.getDescription(), couponEntity.isValid(), couponEntity.isRedeemed());
    }

    // -------------------------------------------------------------------------
    // 5. validateCoupon
    // -------------------------------------------------------------------------
    public boolean validateCoupon(Long userId, String couponId) {
        Optional<CouponEntity> couponOpt = couponRepository.findById(couponId);
        if (!couponOpt.isPresent()) {
            return false;
        }
        CouponEntity couponEntity = couponOpt.get();
        // Validate that the coupon belongs to the user, is valid, and has not been redeemed
        return couponEntity.getUserId().equals(userId) && couponEntity.isValid() && !couponEntity.isRedeemed();
    }

    // -------------------------------------------------------------------------
    // Existing methods for demo purposes
    // -------------------------------------------------------------------------
    public RewardEntity saveDummyReward() {
        RewardEntity reward = new RewardEntity();
        reward.setUserId(1L); // default dummy user
        reward.setName("Dummy Reward");
        reward.setPoints(100);
        return rewardRepository.save(reward);
    }

    public String sendRewardToRouteCalculation(RewardEntity reward) {
        String routeCalculationUrl = "http://route-calculation-service:8080/route/save-reward";
        return restTemplate.postForObject(routeCalculationUrl, reward, String.class);
    }
}
