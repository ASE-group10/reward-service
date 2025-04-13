// File: RewardServiceTest.java
package com.example.reward_service.service;

import com.example.reward_service.dao.RewardDao;
import com.example.reward_service.dao.RewardRepository;
import com.example.reward_service.dao.TotalRewardsRepository;
import com.example.reward_service.dao.CouponRepository;
import com.example.reward_service.dao.UserCouponRepository;
import com.example.reward_service.entity.CouponEntity;
import com.example.reward_service.entity.RewardEntity;
import com.example.reward_service.entity.TotalRewardsEntity;
import com.example.reward_service.entity.UserCouponEntity;
import com.example.reward_service.exception.DatabaseOperationException;
import com.example.reward_service.exception.InsufficientPointsException;
import com.example.reward_service.exception.InvalidRequestException;
import com.example.reward_service.exception.ResourceNotFoundException;
import com.example.reward_service.model.CouponInfo;
import com.example.reward_service.model.RewardRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


class RewardServiceTest {

    @Mock
    private RewardDao rewardDao;

    @Mock
    private RewardRepository rewardRepository;

    @Mock
    private TotalRewardsRepository totalRewardsRepository;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private UserCouponRepository userCouponRepository;

    @InjectMocks
    private RewardService rewardService;

    private RewardRequest rewardRequest;
    private String testUserId = "test-user-id";

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Create RewardRequest with sample data
        rewardRequest = new RewardRequest();
        rewardRequest.setUserId(testUserId);

        RewardRequest.RouteDetails routeDetails = new RewardRequest.RouteDetails();
        routeDetails.setDistance(2.5); // Set 2.5 km distance
        routeDetails.setHealthCompliant(true); // Set health compliance to true

        rewardRequest.setRouteDetails(routeDetails); // Set RouteDetails in RewardRequest
    }

    @Test
    void testValidateAndCalculateReward_Eligible() {
        // Mock the behavior of checkEligibility() in RewardDao
        when(rewardDao.checkEligibility(testUserId, 2.5, true)).thenReturn(true);

        // Mock the saveReward() method in RewardDao
        RewardEntity savedReward = new RewardEntity(testUserId, 25, "Reward saved successfully.");
        when(rewardDao.saveReward(testUserId, 25)).thenReturn(savedReward);

        // Mock rewardRepository.save() method
        when(rewardRepository.save(any(RewardEntity.class))).thenReturn(savedReward);

        // Call the service method
        RewardEntity reward = rewardService.validateAndCalculateReward(testUserId, rewardRequest);

        // Assert the points and the status returned by the service
        assertEquals(25, reward.getPoints(), "Points should be 25.");
        assertEquals("Reward saved successfully.", reward.getStatus(),
                "Status should be 'Reward saved successfully.'");
    }

    @Test
    void testValidateAndCalculateReward_NotEligible() {
        // Mock the behavior of checkEligibility() in RewardDao
        when(rewardDao.checkEligibility(testUserId, 2.5, true)).thenReturn(false);

        // Create and mock the ineligible reward entity
        RewardEntity ineligibleReward = new RewardEntity(testUserId, 0, "User is not eligible for a reward.");
        when(rewardRepository.save(any(RewardEntity.class))).thenReturn(ineligibleReward);

        // Call the service method
        RewardEntity reward = rewardService.validateAndCalculateReward(testUserId, rewardRequest);

        // Assert the points and status when not eligible
        assertEquals(0, reward.getPoints(), "Points should be 0.");
        assertEquals("User is not eligible for a reward.", reward.getStatus(),
                "Status should be 'User is not eligible for a reward.'");
    }

    @Test
    void testGetTotalRewards_WhenUserExists() {
        TotalRewardsEntity existing = new TotalRewardsEntity(testUserId, 100);
        when(totalRewardsRepository.findById(testUserId)).thenReturn(Optional.of(existing));

        TotalRewardsEntity result = rewardService.getTotalRewards(testUserId);
        assertEquals(100, result.getTotalPoints());
    }

    @Test
    void testGetTotalRewards_WhenUserDoesNotExist() {
        when(totalRewardsRepository.findById(testUserId)).thenReturn(Optional.empty());

        TotalRewardsEntity result = rewardService.getTotalRewards(testUserId);
        assertEquals(0, result.getTotalPoints());
        assertEquals(testUserId, result.getUserId());
    }

    @Test
    void testGetEligibleCoupons_DataAccessException() {
        when(rewardRepository.findByUserId(testUserId))
                .thenThrow(new DataAccessException("DB error") {});

        assertThrows(DatabaseOperationException.class, () -> rewardService.getEligibleCoupons(testUserId));
    }

    @Test
    void testRedeemCoupon_CouponNotFound() {
        when(couponRepository.findById("coupon-1")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                rewardService.redeemCoupon(testUserId, "coupon-1"));
    }

    @Test
    void testGetEligibleCoupons_Success() {
        RewardEntity r1 = new RewardEntity(testUserId, 50, "ok");
        RewardEntity r2 = new RewardEntity(testUserId, 30, "ok");
        when(rewardRepository.findByUserId(testUserId)).thenReturn(List.of(r1, r2));

        CouponEntity cheapCoupon = new CouponEntity();
        cheapCoupon.setCouponId("c1");
        cheapCoupon.setCouponType("Discount");
        cheapCoupon.setCouponDesc("10% Off");
        cheapCoupon.setCouponExpiryDateAndTime(LocalDateTime.now().plusDays(1));
        cheapCoupon.setValid(true);
        cheapCoupon.setCouponRewardPoints(50);

        CouponEntity expensiveCoupon = new CouponEntity();
        expensiveCoupon.setCouponId("c2");
        expensiveCoupon.setCouponType("Big");
        expensiveCoupon.setCouponDesc("50% Off");
        expensiveCoupon.setCouponExpiryDateAndTime(LocalDateTime.now().plusDays(1));
        expensiveCoupon.setValid(true);
        expensiveCoupon.setCouponRewardPoints(100);

        when(couponRepository.findAll()).thenReturn(List.of(cheapCoupon, expensiveCoupon));

        List<CouponInfo> coupons = rewardService.getEligibleCoupons(testUserId);
        assertEquals(1, coupons.size());
        assertEquals("c1", coupons.get(0).getCouponId());
    }

    @Test
    void testRedeemCoupon_InvalidCoupon() {
        CouponEntity expired = new CouponEntity();
        expired.setCouponId("c1");
        expired.setCouponType("Expired");
        expired.setCouponDesc("Expired coupon");
        expired.setCouponExpiryDateAndTime(LocalDateTime.now().minusDays(1));
        expired.setValid(false);
        expired.setCouponRewardPoints(50);
        when(couponRepository.findById("c1")).thenReturn(Optional.of(expired));

        assertThrows(InvalidRequestException.class, () ->
                rewardService.redeemCoupon(testUserId, "c1"));
    }

    @Test
    void testRedeemCoupon_InsufficientPoints() {
        CouponEntity valid = new CouponEntity();
        valid.setCouponId("c1");
        valid.setCouponType("Standard");
        valid.setCouponDesc("Valid coupon");
        valid.setCouponExpiryDateAndTime(LocalDateTime.now().plusDays(1));
        valid.setValid(true);
        valid.setCouponRewardPoints(50);
        when(couponRepository.findById("c1")).thenReturn(Optional.of(valid));

        when(userCouponRepository.findByUserIdAndCouponId(testUserId, "c1"))
                .thenReturn(Optional.of(new UserCouponEntity(null, testUserId, "c1", false, null)));

        when(totalRewardsRepository.findById(testUserId))
                .thenReturn(Optional.of(new TotalRewardsEntity(testUserId, 10)));

        assertThrows(InsufficientPointsException.class, () ->
                rewardService.redeemCoupon(testUserId, "c1"));
    }

    @Test
    void testRedeemCoupon_AlreadyRedeemed() {
        CouponEntity valid = new CouponEntity();
        valid.setCouponId("c1");
        valid.setCouponType("Standard");
        valid.setCouponDesc("Already used coupon");
        valid.setCouponExpiryDateAndTime(LocalDateTime.now().plusDays(1));
        valid.setValid(true);
        valid.setCouponRewardPoints(50);
        when(couponRepository.findById("c1")).thenReturn(Optional.of(valid));

        UserCouponEntity redeemed = new UserCouponEntity(null, testUserId, "c1", true, LocalDateTime.now());
        when(userCouponRepository.findByUserIdAndCouponId(testUserId, "c1")).thenReturn(Optional.of(redeemed));

        assertThrows(InvalidRequestException.class, () ->
                rewardService.redeemCoupon(testUserId, "c1"));
    }
}
