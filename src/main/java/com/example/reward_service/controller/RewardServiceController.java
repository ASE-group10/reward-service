package com.example.reward_service.controller;

import com.example.reward_service.dao.RewardRepository;
import com.example.reward_service.entity.RewardEntity;
import com.example.reward_service.model.CouponInfo;
import com.example.reward_service.model.RewardRequest;
import com.example.reward_service.model.RedeemRequest;
import com.example.reward_service.service.RewardService;
import com.example.reward_service.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RewardServiceController {

    private final RewardService rewardService;
    private final RewardRepository rewardRepository;

    @PostMapping("/calculate-reward")
    public RewardEntity calculateReward(@RequestHeader("Authorization") String token,
                                        @RequestBody RewardRequest rewardRequest) {
        String auth0UserId = AuthUtils.getAuth0UserIdFromToken(token);
        rewardRequest.setUserId(auth0UserId);
        return rewardService.validateAndCalculateReward(auth0UserId, rewardRequest);
    }

    @GetMapping("/rewards-history")
    public List<RewardEntity> getRewardsHistory(@RequestHeader("Authorization") String token) {
        String auth0UserId = AuthUtils.getAuth0UserIdFromToken(token);
        return rewardRepository.findByUserId(auth0UserId);
    }

    @GetMapping("/coupons/eligible")
    public List<CouponInfo> getEligibleCoupons(@RequestHeader("Authorization") String token) {
        String auth0UserId = AuthUtils.getAuth0UserIdFromToken(token);
        return rewardService.getEligibleCoupons(auth0UserId);
    }

    // New: Redeem a coupon for the current user, subtracting its cost from totalrewards
    @PostMapping("/coupons/redeem")
    public String redeemCoupon(@RequestHeader("Authorization") String token,
                               @RequestBody RedeemRequest redeemRequest) {
        String auth0UserId = AuthUtils.getAuth0UserIdFromToken(token);
        rewardService.redeemCoupon(auth0UserId, redeemRequest.getCouponId());
        return "Coupon " + redeemRequest.getCouponId() + " redeemed successfully!";
    }
}
