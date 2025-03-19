package com.example.reward_service.controller;

import com.example.reward_service.dao.RewardRepository;
import com.example.reward_service.entity.RewardEntity;
import com.example.reward_service.model.Coupon;
import com.example.reward_service.model.RedeemCouponRequest;
import com.example.reward_service.model.Reward;
import com.example.reward_service.model.RewardRequest;
import com.example.reward_service.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rewards")
public class RewardServiceController {

    private final RewardService rewardService;

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    public RewardServiceController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    // -------------------------------------------------------------------------
    // Existing Endpoints
    // -------------------------------------------------------------------------

    @PostMapping("/validate-reward")
    public Reward validateReward(@RequestBody RewardRequest rewardRequest) {
        return rewardService.validateAndCalculateReward(rewardRequest);
    }

    @GetMapping("/save-and-send-reward")
    public String saveAndSendReward() {
        RewardEntity reward = rewardService.saveDummyReward();
        return rewardService.sendRewardToRouteCalculation(reward);
    }

    @GetMapping
    public List<RewardEntity> getRewards() {
        return rewardRepository.findAll();
    }

    // -------------------------------------------------------------------------
    // New Endpoints
    // -------------------------------------------------------------------------

    // 1. Get reward history for a given user.
    @GetMapping("/history/{userId}")
    public List<RewardEntity> getRewardHistory(@PathVariable("userId") Long userId) {
        return rewardService.getRewardHistory(userId);
    }

    // 2. Get available coupons for a given user.
    @GetMapping("/coupons/{userId}")
    public List<Coupon> getCoupons(@PathVariable("userId") Long userId) {
        return rewardService.getCoupons(userId);
    }

    // 3. Redeem a coupon.
    @PostMapping("/coupons/redeem")
    public Coupon redeemCoupon(@RequestBody RedeemCouponRequest request) {
        return rewardService.redeemCoupon(request.getUserId(), request.getCouponId());
    }

    // 4. Validate a coupon.
    @GetMapping("/coupons/validate")
    public boolean validateCoupon(@RequestParam("userId") Long userId,
                                  @RequestParam("couponId") String couponId) {
        return rewardService.validateCoupon(userId, couponId);
    }
}
