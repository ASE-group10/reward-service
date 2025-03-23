package com.example.reward_service.exception;

public class CouponAlreadyRedeemedException extends RuntimeException {
    public CouponAlreadyRedeemedException(String message) {
        super(message);
    }
}