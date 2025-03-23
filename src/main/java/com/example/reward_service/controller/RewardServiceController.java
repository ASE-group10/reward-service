package com.example.reward_service.controller;

import com.example.reward_service.dao.RewardRepository;
import com.example.reward_service.entity.RewardEntity;
import com.example.reward_service.exception.CouponAlreadyRedeemedException;
import com.example.reward_service.exception.CouponNotFoundException;
import com.example.reward_service.exception.UnauthorizedAccessException;
import com.example.reward_service.model.*;
import com.example.reward_service.service.RewardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rewards")
public class RewardServiceController {

    private final RewardService rewardService;
    private final RewardRepository rewardRepository;

    @Autowired
    public RewardServiceController(RewardService rewardService, RewardRepository rewardRepository) {
        this.rewardService = rewardService;
        this.rewardRepository = rewardRepository;
    }

    // -------------------------------
    // Existing Endpoints (Revised)
    // -------------------------------
    @PostMapping("/validate-reward")
    public Reward validateReward(@Valid @RequestBody RewardRequest rewardRequest, Authentication authentication) {
        Long userId = extractUserId(authentication);
        rewardRequest.setUserId(userId);
        return rewardService.validateAndCalculateReward(rewardRequest);
    }

    @GetMapping("/save-and-send-reward")
    public String saveAndSendReward(Authentication authentication) {
        Long userId = extractUserId(authentication);
        RewardEntity reward = rewardService.saveDummyReward(userId);
        return rewardService.sendRewardToRouteCalculation(reward);
    }

    @GetMapping
    public List<RewardEntity> getRewards() {
        return rewardRepository.findAll();
    }

    // -------------------------------
    // New Endpoints (Revised)
    // -------------------------------
    @GetMapping("/history/{userId}")
    public List<RewardEntity> getRewardHistory(@PathVariable Long userId, Authentication authentication) {
        validateUserOwnership(authentication, userId);
        return rewardService.getRewardHistory(userId);
    }

    @GetMapping("/coupons/{userId}")
    public List<Coupon> getCoupons(@PathVariable Long userId, Authentication authentication) {
        validateUserOwnership(authentication, userId);
        return rewardService.getCoupons(userId);
    }

    @PostMapping("/coupons/redeem")
    @ResponseStatus(HttpStatus.OK)
    public Coupon redeemCoupon(@Valid @RequestBody RedeemCouponRequest request, Authentication authentication) {
        validateUserOwnership(authentication, request.getUserId());
        return rewardService.redeemCoupon(request.getUserId(), request.getCouponId());
    }

    @GetMapping("/coupons/validate")
    public boolean validateCoupon(@RequestParam Long userId,
                                  @RequestParam String couponId,
                                  Authentication authentication) {
        validateUserOwnership(authentication, userId);
        return rewardService.validateCoupon(userId, couponId);
    }

    // -------------------------------
    // Helper Methods
    // -------------------------------
    private Long extractUserId(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String username = (principal instanceof UserDetails) ?
                ((UserDetails) principal).getUsername() : principal.toString();
        return Long.parseLong(username);
    }

    private void validateUserOwnership(Authentication authentication, Long userId) {
        Long authenticatedUserId = extractUserId(authentication);
        if (!authenticatedUserId.equals(userId)) {
            throw new UnauthorizedAccessException("User is not authorized to access this resource.");
        }
    }

    // -------------------------------
    // Exception Handlers
    // -------------------------------
    @ExceptionHandler({CouponNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCouponNotFound(CouponNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler({CouponAlreadyRedeemedException.class, UnauthorizedAccessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequestExceptions(RuntimeException ex) {
        return ex.getMessage();
    }
}