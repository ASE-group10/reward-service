package com.example.reward_service.controller;

import com.example.reward_service.model.Reward;
import com.example.reward_service.model.RewardRequest;
import com.example.reward_service.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reward")
public class RewardServiceController {

    private final RewardService rewardService;

    @Autowired
    public RewardServiceController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @PostMapping("/validate-reward")
    public Reward validateReward(@RequestBody RewardRequest rewardRequest) {
        return rewardService.validateAndCalculateReward(rewardRequest);
    }

    @GetMapping("/hello-reward")
    public String getHelloData() {
        return "Fuck!";
    }
}
