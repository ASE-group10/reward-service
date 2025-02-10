package com.example.reward_service.controller;

import com.example.reward_service.model.RewardRequest;
import com.example.reward_service.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rewards")
public class RewardServiceController {

    @Autowired
    private RewardService rewardService;

    @PostMapping("/register")
    public ResponseEntity<String> registerReward(@RequestBody RewardRequest rewardRequest) {
        int points = rewardService.calculateRewardPoints(rewardRequest.getModeOfTransport());
        return ResponseEntity.ok("User rewarded with " + points + " points for mode: " + rewardRequest.getModeOfTransport());
    }
}
