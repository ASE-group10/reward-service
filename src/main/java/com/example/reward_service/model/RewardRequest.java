package com.example.reward_service.model;

import lombok.Data;

@Data
public class RewardRequest {
    private String userId;
    private RouteDetails routeDetails;

    @Data
    public static class RouteDetails {
        private double distance;
        private boolean healthCompliant;
    }
}