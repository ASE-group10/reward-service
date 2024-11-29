package com.example.reward_service.controller;

import com.example.reward_service.model.Reward;
import com.example.reward_service.model.RewardRequest;
import com.example.reward_service.service.RewardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RewardServiceController {

    private RewardService rewardService;

    @Autowired
    public RewardServiceController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @PostMapping("/validate-reward")
    public Reward validateReward(@RequestBody RewardRequest rewardRequest) {
        // Validate and calculate the reward
        return rewardService.validateAndCalculateReward(rewardRequest);
    }

    @GetMapping("/hello-reward")
    public String getHelloData() {
        // Simple endpoint for testing
        return "Hello from Reward Service!";
    }
}
