package com.example.reward_service.service;

import com.example.reward_service.dao.*;
import com.example.reward_service.entity.*;
import com.example.reward_service.exception.*;
import com.example.reward_service.model.CouponInfo;
import com.example.reward_service.model.RewardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RewardService {

    @Autowired private RewardDao rewardDao;
    @Autowired private CouponRepository couponRepository;
    @Autowired private TotalRewardsRepository totalRewardsRepository;
    @Autowired private RewardRepository rewardRepository;
    @Autowired private UserCouponRepository userCouponRepository;

    public TotalRewardsEntity getTotalRewards(String userId) {
        return totalRewardsRepository.findById(userId)
                .orElse(new TotalRewardsEntity(userId, 0));
    }

    public RewardEntity validateAndCalculateReward(String auth0UserId, RewardRequest rewardRequest) {
        try {
            double distance = rewardRequest.getRouteDetails().getDistance();
            boolean isHealthCompliant = rewardRequest.getRouteDetails().isHealthCompliant();

            if (!rewardDao.checkEligibility(auth0UserId, distance, isHealthCompliant)) {
                RewardEntity ineligible = new RewardEntity(auth0UserId, 0, "User is not eligible");
                return rewardRepository.save(ineligible);
            }

            int points = (int) (distance * 10);
            RewardEntity newReward = rewardDao.saveReward(auth0UserId, points);
            updateTotalRewards(auth0UserId, points);
            return newReward;
        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Reward calculation failed", ex);
        }
    }

    public List<CouponInfo> getEligibleCoupons(String auth0UserId) {
        try {
            int totalPoints = rewardRepository.findByUserId(auth0UserId)
                    .stream()
                    .mapToInt(RewardEntity::getPoints)
                    .sum();

            return couponRepository.findAll().stream()
                    .filter(coupon -> coupon.getCouponRewardPoints() < totalPoints)
                    .map(this::convertToCouponInfo)
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Failed to fetch coupons", ex);
        }
    }

    public void redeemCoupon(String auth0UserId, String couponId) {
        CouponEntity coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found: " + couponId));

        validateCoupon(coupon);
        checkExistingRedemption(auth0UserId, couponId);
        validatePointsBalance(auth0UserId, coupon);

        updateTotalRewards(auth0UserId, -coupon.getCouponRewardPoints());
        saveUserCouponRedemption(auth0UserId, couponId);
    }

    private void validateCoupon(CouponEntity coupon) {
        if (!coupon.isValid() || coupon.getCouponExpiryDateAndTime().isBefore(LocalDateTime.now())) {
            throw new InvalidRequestException("Invalid or expired coupon");
        }
    }

    private void checkExistingRedemption(String userId, String couponId) {
        userCouponRepository.findByUserIdAndCouponId(userId, couponId)
                .filter(UserCouponEntity::isRedeemed)
                .ifPresent(uc -> {
                    throw new InvalidRequestException("Coupon already redeemed");
                });
    }

    private void validatePointsBalance(String userId, CouponEntity coupon) {
        if (getUserTotalPoints(userId) < coupon.getCouponRewardPoints()) {
            throw new InsufficientPointsException("Insufficient reward points");
        }
    }

    private void saveUserCouponRedemption(String userId, String couponId) {
        UserCouponEntity userCoupon = userCouponRepository.findByUserIdAndCouponId(userId, couponId)
                .orElse(new UserCouponEntity(null, userId, couponId, false, null));
        userCoupon.setRedeemed(true);
        userCoupon.setRedeemedAt(LocalDateTime.now());
        userCouponRepository.save(userCoupon);
    }

    private CouponInfo convertToCouponInfo(CouponEntity coupon) {
        return new CouponInfo(
                coupon.getCouponId(),
                coupon.getCouponType(),
                coupon.getCouponDesc(),
                coupon.getCouponExpiryDateAndTime(),
                coupon.getCouponStatus(),
                coupon.getCouponRewardPoints()
        );
    }

    // Helper methods
    private int getUserTotalPoints(String userId) {
        return totalRewardsRepository.findById(userId)
                .map(TotalRewardsEntity::getTotalPoints)
                .orElse(0);
    }

    private void updateTotalRewards(String userId, int points) {
        TotalRewardsEntity tre = totalRewardsRepository.findById(userId)
                .orElse(new TotalRewardsEntity(userId, 0));
        tre.setTotalPoints(tre.getTotalPoints() + points);
        totalRewardsRepository.save(tre);
    }
}