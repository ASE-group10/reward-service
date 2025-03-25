package com.example.reward_service.service;

import com.example.reward_service.dao.RewardDao;
import com.example.reward_service.entity.RewardEntity;
import com.example.reward_service.model.Reward;
import com.example.reward_service.model.RewardRequest;
import com.example.reward_service.model.RouteDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RewardServiceTest {

    @Mock
    private RewardDao rewardDao;  // Mocking RewardDao

    @InjectMocks
    private RewardService rewardService;  // RewardService with injected mocked RewardDao

    private RewardRequest rewardRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks

        // Create RewardRequest with sample data
        rewardRequest = new RewardRequest();
        rewardRequest.setUserId("eeowfhewofh");

        RouteDetails routeDetails = new RouteDetails();
        routeDetails.setDistance(2.5);  // Set 2.5 km distance
        routeDetails.setHealthCompliant(true);  // Set health compliance to true

        rewardRequest.setRouteDetails(routeDetails);  // Set RouteDetails in RewardRequest
    }

    @Test
    public void testValidateAndCalculateReward_Eligible() {
        // Mock the behavior of checkEligibility() in RewardDao
        when(rewardDao.checkEligibility("efefsdfds", 2.5, true)).thenReturn(true);

        // Mock the saveReward() method in RewardDao
        when(rewardDao.saveReward("dsadasd", 25)).thenReturn(new RewardEntity("efwef", 25, "Reward saved successfully."));

        // Call the service method
        RewardEntity reward = rewardService.validateAndCalculateReward("ewqwe",rewardRequest);

        // Assert the points and the status returned by the service
        assertEquals(25, reward.getPoints(), "Points should be 25.");
        assertEquals("Reward saved successfully.", reward.getStatus(), "Status should be 'Reward saved successfully.'");
    }

    @Test
    public void testValidateAndCalculateReward_NotEligible() {
        // Mock the behavior of checkEligibility() in RewardDao
        when(rewardDao.checkEligibility("123L", 2.5, true)).thenReturn(false);

        // Call the service method
        RewardEntity reward = rewardService.validateAndCalculateReward("ewrwer",rewardRequest);

        // Assert the points and status when not eligible
        assertEquals(0, reward.getPoints(), "Points should be 0.");
        assertEquals("User is not eligible for a reward.", reward.getStatus(), "Status should be 'User is not eligible for a reward.'");
    }
}
