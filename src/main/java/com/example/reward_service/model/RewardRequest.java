package com.example.reward_service.model;

public class RewardRequest {
    private Long userId;
    private RouteDetails routeDetails;

    public RewardRequest() {}

    public RewardRequest(Long userId, RouteDetails routeDetails) {
        this.userId = userId;
        this.routeDetails = routeDetails;
    }

    // Getters and Setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public RouteDetails getRouteDetails() {
        return routeDetails;
    }

    public void setRouteDetails(RouteDetails routeDetails) {
        this.routeDetails = routeDetails;
    }
}
