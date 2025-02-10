package com.example.reward_service.model;

public class RewardRequest {
    private String userId;
    private String modeOfTransport;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getModeOfTransport() { return modeOfTransport; }
    public void setModeOfTransport(String modeOfTransport) { this.modeOfTransport = modeOfTransport; }
}
