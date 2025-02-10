package com.example.reward_service;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Map;

public class RewardServiceHandler implements RequestHandler<Map<String, Object>, String> {

    @Override
    public String handleRequest(Map<String, Object> input, Context context) {
        String modeOfTransport = (String) input.get("modeOfTransport");
        int points = calculateRewardPoints(modeOfTransport);
        return "User rewarded with " + points + " points for mode: " + modeOfTransport;
    }

    private int calculateRewardPoints(String modeOfTransport) {
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
