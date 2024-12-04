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

    private double distance;
    private boolean healthCompliant;

    // Lombok will generate getters, setters, toString, equals, and hashCode methods
}
