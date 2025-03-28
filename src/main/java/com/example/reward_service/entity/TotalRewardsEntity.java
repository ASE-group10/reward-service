package com.example.reward_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "totalrewards")
public class TotalRewardsEntity {

    @Id
    @Column(name = "userid", nullable = false)
    private String userId;

    @Column(name = "totalpoints")
    private int totalPoints;
}
