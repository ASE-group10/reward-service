package com.example.reward_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
public class CouponEntity {
    @Id
    private String couponId;

    private Long userId;
    private String couponType;
    private String couponDesc;

    private LocalDateTime couponExpiryDateAndTime;
    private String couponStatus;
    private int couponRewardPoints;

    private boolean valid;
    private boolean redeemed;

    // Constructors
    public CouponEntity() {}

    public CouponEntity(String couponId, Long userId, String couponType, String couponDesc,
                        LocalDateTime couponExpiryDateAndTime, String couponStatus, int couponRewardPoints,
                        boolean valid, boolean redeemed) {
        this.couponId = couponId;
        this.userId = userId;
        this.couponType = couponType;
        this.couponDesc = couponDesc;
        this.couponExpiryDateAndTime = couponExpiryDateAndTime;
        this.couponStatus = couponStatus;
        this.couponRewardPoints = couponRewardPoints;
        this.valid = valid;
        this.redeemed = redeemed;
    }

    // Getters and Setters for all fields

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    public LocalDateTime getCouponExpiryDateAndTime() {
        return couponExpiryDateAndTime;
    }

    public void setCouponExpiryDateAndTime(LocalDateTime couponExpiryDateAndTime) {
        this.couponExpiryDateAndTime = couponExpiryDateAndTime;
    }

    public String getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus;
    }

    public int getCouponRewardPoints() {
        return couponRewardPoints;
    }

    public void setCouponRewardPoints(int couponRewardPoints) {
        this.couponRewardPoints = couponRewardPoints;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isRedeemed() {
        return redeemed;
    }

    public void setRedeemed(boolean redeemed) {
        this.redeemed = redeemed;
    }
}
