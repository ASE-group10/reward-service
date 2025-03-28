package com.example.reward_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
public class CouponEntity {

    @Id
    @Column(name = "couponid", nullable = false)
    private String couponId;

    @Column(name = "coupontype")
    private String couponType;

    @Column(name = "coupondesc")
    private String couponDesc;

    @Column(name = "couponexpirydateandtime")
    private LocalDateTime couponExpiryDateAndTime;

    @Column(name = "couponstatus")
    private String couponStatus;

    @Column(name = "couponrewardpoints")
    private int couponRewardPoints;

    @Column(name = "valid")
    private boolean valid;

    // Constructors
    public CouponEntity() {}

    public CouponEntity(String couponId, String couponType, String couponDesc,
                        LocalDateTime couponExpiryDateAndTime, String couponStatus, int couponRewardPoints,
                        boolean valid, boolean redeemed) {
        this.couponId = couponId;
        this.couponType = couponType;
        this.couponDesc = couponDesc;
        this.couponExpiryDateAndTime = couponExpiryDateAndTime;
        this.couponStatus = couponStatus;
        this.couponRewardPoints = couponRewardPoints;
        this.valid = valid;
    }

    // Getters and Setters
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

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
