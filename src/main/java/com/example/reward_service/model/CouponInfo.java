package com.example.reward_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CouponInfo {
    private String couponId;
    private String couponType;
    private String couponDesc;
    private LocalDateTime expiryDate;
    private String status;
    private int requiredPoints;
}