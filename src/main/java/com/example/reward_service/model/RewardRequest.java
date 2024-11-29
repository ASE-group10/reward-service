package com.example.reward_service.model;

public class RewardRequest {

    private Long userId;
    private RouteDetails routeDetails;  // RouteDetails object which contains distance and health compliance

    // Getter and setter for userId
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Getter and setter for routeDetails
    public RouteDetails getRouteDetails() {
        return routeDetails;
    }

    public void setRouteDetails(RouteDetails routeDetails) {
        this.routeDetails = routeDetails;
    }
}
