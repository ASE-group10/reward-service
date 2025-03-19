package com.example.reward_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "coupons")
public class CouponEntity {
    @Id
    private String couponId;  // assumed to be provided externally

    private Long userId;
    private String description;
    private boolean valid;   // true if coupon is valid (e.g., not expired)
    private boolean redeemed; // true if coupon has been redeemed

    // Constructors
    public CouponEntity() {}

    public CouponEntity(String couponId, Long userId, String description, boolean valid, boolean redeemed) {
        this.couponId = couponId;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
