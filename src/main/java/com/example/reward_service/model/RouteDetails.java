package com.example.reward_service.model;

public class RouteDetails {
    private double distance;
    private boolean healthCompliant;

    // Getters and Setters
    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public boolean isHealthCompliant() {
        return healthCompliant;
    }
    public void setHealthCompliant(boolean healthCompliant) {
        this.healthCompliant = healthCompliant;
    }
}
