package com.example.reward_service.model;

public class Coupon {
    private String couponId;
    private String description;
    private boolean valid;
    private boolean redeemed;

    public Coupon() {}

    public Coupon(String couponId, String description, boolean valid, boolean redeemed) {
        this.couponId = couponId;
        this.description = description;
        this.valid = valid;
        this.redeemed = redeemed;
    }

    // Getters and Setters

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
