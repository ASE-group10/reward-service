package com.example.reward_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_coupons")
public class UserCouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;    // Auth0 user ID
    private String couponId;  // Points to CouponEntity.couponId

    private boolean redeemed;
    private LocalDateTime redeemedAt;
}
