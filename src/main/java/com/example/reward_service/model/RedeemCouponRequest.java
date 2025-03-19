package com.example.reward_service.model;

public class RedeemCouponRequest {
    private Long userId;
    private String couponId;

    public RedeemCouponRequest() {}

    public RedeemCouponRequest(Long userId, String couponId) {
        this.userId = userId;
        this.couponId = couponId;
    }

    // Getters and Setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }
}
