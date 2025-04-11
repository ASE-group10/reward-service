package com.example.reward_service.controller;

import com.example.reward_service.dao.RewardRepository;
import com.example.reward_service.entity.RewardEntity;
import com.example.reward_service.entity.TotalRewardsEntity;
import com.example.reward_service.exception.AuthenticationException;
import com.example.reward_service.model.RewardRequest;
import com.example.reward_service.model.RedeemRequest;
import com.example.reward_service.model.CouponInfo;
import com.example.reward_service.service.RewardService;
import com.example.reward_service.utils.AuthUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RewardServiceControllerTest {

    @Mock
    private RewardService rewardService;

    @Mock
    private RewardRepository rewardRepository;

    @Mock
    private AuthUtils authUtils;

    @InjectMocks
    private RewardServiceController rewardServiceController;

    private final String validToken = "validToken";
    private final String userId = "userId";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(authUtils.getAuth0UserIdFromToken(validToken)).thenReturn(userId);
    }

    @Test
    void testCalculateReward_ValidRequest() {
        // Create a reward request with the correct structure
        RewardRequest rewardRequest = new RewardRequest();
        rewardRequest.setUserId(userId);
        RewardRequest.RouteDetails routeDetails = new RewardRequest.RouteDetails();
        routeDetails.setDistance(10.0);
        routeDetails.setHealthCompliant(true);
        rewardRequest.setRouteDetails(routeDetails);

        // Create a reward entity with the correct structure
        RewardEntity rewardEntity = new RewardEntity();
        rewardEntity.setId(1L);
        rewardEntity.setUserId(userId);
        rewardEntity.setPoints(100);
        rewardEntity.setStatus("Reward saved successfully.");
        rewardEntity.setName("Test Reward");

        when(rewardService.validateAndCalculateReward(userId, rewardRequest)).thenReturn(rewardEntity);

        ResponseEntity<RewardEntity> response = rewardServiceController.calculateReward(validToken, rewardRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(rewardEntity, response.getBody());
    }

    @Test
    void testGetRewardsHistory_ValidToken() {
        // Create a reward entity with the correct constructor
        RewardEntity rewardEntity = new RewardEntity(userId, 100, "Reward saved successfully.");
        rewardEntity.setId(1L);

        List<RewardEntity> rewards = Collections.singletonList(rewardEntity);
        when(rewardRepository.findByUserId(userId)).thenReturn(rewards);

        ResponseEntity<List<RewardEntity>> response = rewardServiceController.getRewardsHistory(validToken);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(rewards, response.getBody());
    }

    @Test
    void testGetEligibleCoupons_ValidToken() {
        // Create a coupon info with the correct constructor parameters
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(30);
        CouponInfo couponInfo = new CouponInfo("couponId", "Discount", "10% off", expiryDate, "Active", 10);

        List<CouponInfo> coupons = Collections.singletonList(couponInfo);
        when(rewardService.getEligibleCoupons(userId)).thenReturn(coupons);

        ResponseEntity<List<CouponInfo>> response = rewardServiceController.getEligibleCoupons(validToken);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(coupons, response.getBody());
    }

    @Test
    void testRedeemCoupon_ValidRequest() {
        RedeemRequest redeemRequest = new RedeemRequest();
        String couponId = "couponId";
        redeemRequest.setCouponId(couponId);

        ResponseEntity<String> response = rewardServiceController.redeemCoupon(validToken, redeemRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Coupon " + couponId + " redeemed successfully!", response.getBody());
        verify(rewardService).redeemCoupon(userId, couponId);
    }

    @Test
    void testGetTotalRewards_ValidToken() {
        TotalRewardsEntity totalRewardsEntity = new TotalRewardsEntity(userId, 500);
        when(rewardService.getTotalRewards(userId)).thenReturn(totalRewardsEntity);

        ResponseEntity<TotalRewardsEntity> response = rewardServiceController.getTotalRewards(validToken);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(totalRewardsEntity, response.getBody());
    }

    @Test
    void testCalculateReward_InvalidToken() {
        when(authUtils.getAuth0UserIdFromToken("invalidToken")).thenThrow(new AuthenticationException("Invalid token"));

        assertThrows(AuthenticationException.class, () -> {
            rewardServiceController.calculateReward("invalidToken", new RewardRequest());
        });
    }
}
