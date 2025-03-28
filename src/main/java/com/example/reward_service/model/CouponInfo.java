package com.example.reward_service.model;

import java.time.LocalDateTime;

public class CouponInfo {
    private String couponId;
    private String couponType;
    private String couponDesc;
    private LocalDateTime couponExpiryDateAndTime;
    private String couponStatus;
    private int couponRewardPoints;

    public CouponInfo(String couponId, String couponType, String couponDesc,
                      LocalDateTime couponExpiryDateAndTime, String couponStatus, int couponRewardPoints) {
        this.couponId = couponId;
        this.couponType = couponType;
        this.couponDesc = couponDesc;
        this.couponExpiryDateAndTime = couponExpiryDateAndTime;
        this.couponStatus = couponStatus;
        this.couponRewardPoints = couponRewardPoints;
    }

    // Getters and setters (or use Lombok @Data)

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
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
}
