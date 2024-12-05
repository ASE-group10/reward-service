package com.example.reward_service.controller;

import com.example.reward_service.dao.RewardRepository;
import com.example.reward_service.entity.RewardEntity;
import com.example.reward_service.model.Reward;
import com.example.reward_service.model.RewardRequest;
import com.example.reward_service.service.RewardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RewardServiceController {

    private RewardService rewardService;
    @Autowired
    private RewardRepository rewardRepository;

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
    @GetMapping("/save-and-send-reward")
    public String saveAndSendReward() {
        RewardEntity reward = rewardService.saveDummyReward();
        return rewardService.sendRewardToRouteCalculation(reward);
    }
    @GetMapping("/rewards")
    public List<RewardEntity> getRewards() {
        // Fetch all rewards from the database and return them
        return rewardRepository.findAll();
    }

}
