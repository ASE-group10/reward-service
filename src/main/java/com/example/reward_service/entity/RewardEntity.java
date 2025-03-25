package com.example.reward_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RewardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;  // Auth0 User ID
    private int points;
    private String status;  // Reward status (e.g., "Reward saved successfully." or error message)
    private String name;

    // Default constructor
    public RewardEntity() {}

    // Constructor with userId and points (default status)
    public RewardEntity(String userId, int points) {
        this.userId = userId;
        this.points = points;
        this.status = "Reward saved successfully.";
    }

    // Constructor with userId, points, and custom status
    public RewardEntity(String userId, int points, String status) {
        this.userId = userId;
        this.points = points;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
