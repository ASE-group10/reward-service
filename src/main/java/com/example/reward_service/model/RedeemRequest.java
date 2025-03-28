package com.example.reward_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RedeemRequest {
    private String couponId;
}
