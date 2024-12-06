package com.example.reward_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class RouteDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Defines the primary key generation strategy
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    private double distance;
    private boolean healthCompliant;

    // Lombok will generate getters, setters, toString, equals, and hashCode methods
}
