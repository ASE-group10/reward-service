// File: RewardServiceTest.java
package com.example.reward_service.service;

import com.example.reward_service.dao.RewardDao;
import com.example.reward_service.dao.RewardRepository;
import com.example.reward_service.dao.TotalRewardsRepository;
import com.example.reward_service.dao.CouponRepository;
import com.example.reward_service.dao.UserCouponRepository;
import com.example.reward_service.entity.RewardEntity;
import com.example.reward_service.model.RewardRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    public void setup() {
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
    public void testValidateAndCalculateReward_Eligible() {
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
    public void testValidateAndCalculateReward_NotEligible() {
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
}
