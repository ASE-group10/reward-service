package com.example.reward_service.model;

public class RewardRequest {
    private String userId;  // Auth0 UserID (optional – can be set in the controller)
    private RouteDetails routeDetails;  // Contains distance and health compliance details

    // Getters and Setters
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public RouteDetails getRouteDetails() {
        return routeDetails;
    }
    public void setRouteDetails(RouteDetails routeDetails) {
        this.routeDetails = routeDetails;
    }
}
