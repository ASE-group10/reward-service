package com.example.reward_service.model;

import lombok.Data;

@Data
public class Reward {
    private String userId;  // Auth0 UserID
    private int points;
    private String status;  // Reward status (Valid, Invalid, etc.)

    public Reward() {}

    public Reward(String userId, int points, String status) {
        this.userId = userId;
        this.points = points;
        this.status = status;
    }
}
