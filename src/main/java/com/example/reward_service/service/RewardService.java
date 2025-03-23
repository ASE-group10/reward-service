package com.example.reward_service.service;

import com.example.reward_service.dao.*;
import com.example.reward_service.entity.*;
import com.example.reward_service.exception.*;
import com.example.reward_service.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RewardService {

    private final RewardDao rewardDao;
    private final RewardRepository rewardRepository;
    private final CouponRepository couponRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public RewardService(RewardDao rewardDao, RewardRepository rewardRepository,
                         CouponRepository couponRepository, RestTemplate restTemplate) {
        this.rewardDao = rewardDao;
        this.rewardRepository = rewardRepository;
        this.couponRepository = couponRepository;
        this.restTemplate = restTemplate;
    }

    // -------------------------------
    // Core Methods (Revised)
    // -------------------------------
    @Transactional
    public Reward validateAndCalculateReward(RewardRequest rewardRequest) {
        Long userId = rewardRequest.getUserId();
        double distance = rewardRequest.getRouteDetails().getDistance();
        boolean isHealthCompliant = rewardRequest.getRouteDetails().isHealthCompliant();

        boolean isEligible = rewardDao.checkEligibility(userId, distance, isHealthCompliant);
        if (!isEligible) {
            return new Reward(0, "User is not eligible for a reward.");
        }

        int points = (int) (distance * 10);
        return rewardDao.saveReward(userId, points);
    }

    @Transactional(readOnly = true)
    public List<RewardEntity> getRewardHistory(Long userId) {
        return rewardRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Coupon> getCoupons(Long userId) {
        return couponRepository.findByUserId(userId).stream()
                .map(entity -> new Coupon(
                        entity.getCouponId(),
                        entity.getDescription(),
                        entity.isValid(),
                        entity.isRedeemed()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public Coupon redeemCoupon(Long userId, String couponId) {
        CouponEntity couponEntity = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found."));

        if (!couponEntity.getUserId().equals(userId)) {
            throw new UnauthorizedAccessException("Coupon does not belong to the user.");
        }

        if (couponEntity.isRedeemed()) {
            throw new CouponAlreadyRedeemedException("Coupon already redeemed.");
        }

        couponEntity.setRedeemed(true);
        couponEntity.setValid(false);
        couponRepository.save(couponEntity);

        return new Coupon(
                couponEntity.getCouponId(),
                couponEntity.getDescription(),
                couponEntity.isValid(),
                couponEntity.isRedeemed()
        );
    }

    @Transactional(readOnly = true)
    public boolean validateCoupon(Long userId, String couponId) {
        return couponRepository.findById(couponId)
                .map(coupon -> coupon.getUserId().equals(userId) && coupon.isValid() && !coupon.isRedeemed())
                .orElse(false);
    }

    // -------------------------------
    // Existing Demo Methods
    // -------------------------------
    @Transactional
    public RewardEntity saveDummyReward(Long userId) {
        RewardEntity reward = new RewardEntity();
        reward.setUserId(userId);
        reward.setName("Dummy Reward");
        reward.setPoints(100);
        return rewardRepository.save(reward);
    }

    public String sendRewardToRouteCalculation(RewardEntity reward) {
        String routeCalculationUrl = "http://route-calculation-service:8080/route/save-reward";
        return restTemplate.postForObject(routeCalculationUrl, reward, String.class);
    }
}