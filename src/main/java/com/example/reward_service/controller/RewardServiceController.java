package com.example.reward_service.controller;

import com.example.reward_service.dao.RewardRepository;
import com.example.reward_service.entity.RewardEntity;
import com.example.reward_service.entity.TotalRewardsEntity;
import com.example.reward_service.exception.AuthenticationException;
import com.example.reward_service.model.*;
import com.example.reward_service.service.RewardService;
import com.example.reward_service.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RewardServiceController {

    private final RewardService rewardService;
    private final RewardRepository rewardRepository;

    @PostMapping("/calculate-reward")
    public ResponseEntity<RewardEntity> calculateReward(
            @RequestHeader("Authorization") String token,
            @RequestBody RewardRequest rewardRequest) {
        String auth0UserId = AuthUtils.getAuth0UserIdFromToken(token);
        rewardRequest.setUserId(auth0UserId);
        return ResponseEntity.ok(rewardService.validateAndCalculateReward(auth0UserId, rewardRequest));
    }

    @GetMapping("/rewards-history")
    public ResponseEntity<List<RewardEntity>> getRewardsHistory(
            @RequestHeader("Authorization") String token) {
        String auth0UserId = AuthUtils.getAuth0UserIdFromToken(token);
        return ResponseEntity.ok(rewardRepository.findByUserId(auth0UserId));
    }

    @GetMapping("/coupons/eligible")
    public ResponseEntity<List<CouponInfo>> getEligibleCoupons(
            @RequestHeader("Authorization") String token) {
        String auth0UserId = AuthUtils.getAuth0UserIdFromToken(token);
        return ResponseEntity.ok(rewardService.getEligibleCoupons(auth0UserId));
    }

    @PostMapping("/coupons/redeem")
    public ResponseEntity<String> redeemCoupon(
            @RequestHeader("Authorization") String token,
            @RequestBody RedeemRequest redeemRequest) {
        String auth0UserId = AuthUtils.getAuth0UserIdFromToken(token);
        rewardService.redeemCoupon(auth0UserId, redeemRequest.getCouponId());
        return ResponseEntity.ok("Coupon " + redeemRequest.getCouponId() + " redeemed successfully!");
    }

    @GetMapping("/total-rewards")
    public ResponseEntity<TotalRewardsEntity> getTotalRewards(
            @RequestHeader("Authorization") String token) {
        String auth0UserId = AuthUtils.getAuth0UserIdFromToken(token);
        TotalRewardsEntity totalRewardsEntity = rewardService.getTotalRewards(auth0UserId);
        return ResponseEntity.ok(totalRewardsEntity);
    }
}