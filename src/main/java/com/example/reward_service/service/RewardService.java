package com.example.reward_service.service;

import com.example.reward_service.dao.*;
import com.example.reward_service.entity.CouponEntity;
import com.example.reward_service.entity.RewardEntity;
import com.example.reward_service.entity.TotalRewardsEntity;
import com.example.reward_service.entity.UserCouponEntity;
import com.example.reward_service.model.CouponInfo;
import com.example.reward_service.model.RewardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RewardService {

    @Autowired
    private RewardDao rewardDao;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private TotalRewardsRepository totalRewardsRepository;

    @Autowired
    private RewardRepository rewardRepository;
    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private RestTemplate restTemplate;

    public RewardEntity validateAndCalculateReward(String auth0UserId, RewardRequest rewardRequest) {
        double distance = rewardRequest.getRouteDetails().getDistance();
        boolean isHealthCompliant = rewardRequest.getRouteDetails().isHealthCompliant();

        // 1) Check eligibility based on distance, compliance, etc.
        boolean isEligible = rewardDao.checkEligibility(auth0UserId, distance, isHealthCompliant);

        if (!isEligible) {
            // Create a 0-point record in your "rewards" table
            RewardEntity ineligible = new RewardEntity(auth0UserId, 0, "User is not eligible for a reward.");
            ineligible.setName("Ineligible Reward");
            return rewardRepository.save(ineligible);
        }

        // 2) Calculate how many points to award, e.g. 10 points per km
        int points = (int) (distance * 10);

        // 3) Save the new reward record
        RewardEntity newReward = rewardDao.saveReward(auth0UserId, points);

        // 4) Also update the total reward points in the totalrewards table
        updateTotalRewards(auth0UserId, points);

        return newReward;
    }

    // New method: getEligibleCoupons
    // It computes the total reward points for the user and then returns only the coupons
    // whose couponRewardPoints are less than the total reward points.
    public List<CouponInfo> getEligibleCoupons(String auth0UserId) {
        // Calculate the user's total reward points from their rewards.
        List<RewardEntity> rewards = rewardRepository.findByUserId(auth0UserId);
        int totalRewardPoints = rewards.stream()
                .mapToInt(RewardEntity::getPoints)
                .sum();

        // Since the coupons table is hardcoded, fetch all coupons.
        List<CouponEntity> coupons = couponRepository.findAll();

        // Filter coupons: include only those with couponRewardPoints less than totalRewardPoints.
        return coupons.stream()
                .filter(coupon -> coupon.getCouponRewardPoints() < totalRewardPoints)
                .map(coupon -> new CouponInfo(
                        coupon.getCouponId(),
                        coupon.getCouponType(),
                        coupon.getCouponDesc(),
                        coupon.getCouponExpiryDateAndTime(),
                        coupon.getCouponStatus(),
                        coupon.getCouponRewardPoints()
                ))
                .collect(Collectors.toList());
    }
    /**
     * Redeem a specific coupon, subtract its cost from user's totalrewards,
     * and record that this user has redeemed that coupon.
     */
    public void redeemCoupon(String auth0UserId, String couponId) {
        // 1) Fetch the coupon
        CouponEntity coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found: " + couponId));

        // 2) Basic validity checks
        if (!coupon.isValid() || coupon.getCouponExpiryDateAndTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Coupon is invalid or expired.");
        }

        // 3) Check if user already redeemed it
        userCouponRepository.findByUserIdAndCouponId(auth0UserId, couponId)
                .filter(UserCouponEntity::isRedeemed)
                .ifPresent(uc -> {
                    throw new RuntimeException("You have already redeemed this coupon.");
                });

        // 4) Check if user has enough total points
        int userPoints = getUserTotalPoints(auth0UserId);
        if (userPoints < coupon.getCouponRewardPoints()) {
            throw new RuntimeException("Not enough reward points to redeem this coupon.");
        }

        // 5) Subtract cost from totalrewards
        updateTotalRewards(auth0UserId, -coupon.getCouponRewardPoints());

        // 6) Mark that user has redeemed the coupon in user_coupons
        UserCouponEntity userCoupon = userCouponRepository.findByUserIdAndCouponId(auth0UserId, couponId)
                .orElse(new UserCouponEntity(null, auth0UserId, couponId, false, null));

        userCoupon.setRedeemed(true);
        userCoupon.setRedeemedAt(LocalDateTime.now());
        userCouponRepository.save(userCoupon);

        // 7) Optionally, change the coupon’s status if you want to mark it used globally
        // coupon.setCouponStatus("REDEEMED"); // if you want to show globally
        // couponRepository.save(coupon);
    }

    // ------------------------------------------------------------------------
    // Helper Methods
    // ------------------------------------------------------------------------

    private int getUserTotalPoints(String userId) {
        // If no row found in totalrewards for this user, default to 0
        return totalRewardsRepository.findById(userId)
                .map(TotalRewardsEntity::getTotalPoints)
                .orElse(0);
    }

    private void updateTotalRewards(String userId, int pointsToAdd) {
        TotalRewardsEntity tre = totalRewardsRepository.findById(userId)
                .orElse(new TotalRewardsEntity(userId, 0));
        tre.setTotalPoints(tre.getTotalPoints() + pointsToAdd);
        totalRewardsRepository.save(tre);
    }
}
