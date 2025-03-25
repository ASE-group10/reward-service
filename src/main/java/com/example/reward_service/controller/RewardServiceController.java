package com.example.reward_service.controller;

import com.example.reward_service.dao.RewardRepository;
import com.example.reward_service.entity.RewardEntity;
import com.example.reward_service.model.RewardRequest;
import com.example.reward_service.service.RewardService;
import com.example.reward_service.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RewardServiceController {

    @Autowired
    private RewardService rewardService;

    @Autowired
    private RewardRepository rewardRepository;

    @PostMapping("/validate-reward")
    public RewardEntity validateReward(@RequestHeader("Authorization") String token, @RequestBody RewardRequest rewardRequest) {
        // Extract Auth0 UserID from the JWT token.
        String auth0UserId = AuthUtils.getAuth0UserIdFromToken(token);
        // Optionally, set the extracted userId into the request model.
        rewardRequest.setUserId(auth0UserId);
        // Validate, calculate, and save the reward entry in the database.
        return rewardService.validateAndCalculateReward(auth0UserId, rewardRequest);
    }
    @GetMapping("/rewards")
    public List<RewardEntity> getRewards(@RequestHeader("Authorization") String token) {
        String auth0UserId = AuthUtils.getAuth0UserIdFromToken(token);
        return rewardRepository.findByUserId(auth0UserId);
    }
}
