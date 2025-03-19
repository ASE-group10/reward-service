package com.example.reward_service.model;

public class Reward {
    private int points;
    private String message;

    public Reward() {}

    public Reward(int points, String message) {
        this.points = points;
        this.message = message;
    }

    // Getters and Setters

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
