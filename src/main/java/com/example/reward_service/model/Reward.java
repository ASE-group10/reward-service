package com.example.reward_service.model;

public class Reward {
    private int points;
    private String status;

    public Reward() {}

    public Reward(int points, String status) {
        this.points = points;
        this.status = status;
    }

    // Getters and Setters
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
