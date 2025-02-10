package com.example.reward_service.service;

import org.springframework.stereotype.Service;

@Service
public class RewardService {

    public int calculateRewardPoints(String modeOfTransport) {
        switch (modeOfTransport.toLowerCase()) {
            case "car":
                return 4;
            case "bus":
                return 6;
            case "cycle":
                return 8;
            case "walk":
                return 10;
            case "bike":
                return 2;
            default:
                return 0;
        }
    }
}
