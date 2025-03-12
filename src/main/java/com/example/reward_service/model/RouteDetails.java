package com.example.reward_service.model;

public class RouteDetails {
    private double distance;
    private boolean healthCompliant;

    // Constructor
    public RouteDetails() {
    }

    public RouteDetails(double distance, boolean healthCompliant) {
        this.distance = distance;
        this.healthCompliant = healthCompliant;
    }

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

    // Method to validate distance
    public boolean isValidDistance() {
        return distance > 0;
    }
}
