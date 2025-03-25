package com.example.reward_service.model;

public class Reward {
    private String userId;  // Auth0 UserID
    private int points;
    private String status;  // Reward status (Valid, Invalid, etc.)

    // Default constructor
    public Reward() {}

    // Constructor with parameters (including userId)
    public Reward(String userId, int points, String status) {
        this.userId = userId;
        this.points = points;
        this.status = status;
    }

    // Getter and Setter for userId
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Getter and Setter for points
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    // Getter and Setter for status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
