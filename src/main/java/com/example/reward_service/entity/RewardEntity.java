package com.example.reward_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rewards")
public class RewardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String name;
    private int points;

    // Constructors
    public RewardEntity() {}

    public RewardEntity(Long userId, String name, int points) {
        this.userId = userId;
        this.name = name;
        this.points = points;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
