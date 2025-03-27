package com.example.reward_service.service;

import com.example.reward_service.dao.CouponRepository;
import com.example.reward_service.dao.RewardDao;
import com.example.reward_service.dao.RewardRepository;
import com.example.reward_service.entity.CouponEntity;
import com.example.reward_service.entity.RewardEntity;
import com.example.reward_service.model.CouponInfo;
import com.example.reward_service.model.RewardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class RewardService {

    @Autowired
    private RewardDao rewardDao;
    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private RestTemplate restTemplate;

    public RewardEntity validateAndCalculateReward(String auth0UserId, RewardRequest rewardRequest) {
        double distance = rewardRequest.getRouteDetails().getDistance();
        boolean isHealthCompliant = rewardRequest.getRouteDetails().isHealthCompliant();

        // Check eligibility based on distance and health compliance.
        boolean isEligible = rewardDao.checkEligibility(auth0UserId, distance, isHealthCompliant);
        RewardEntity rewardEntity;
        if (isEligible) {
            // Calculate points (e.g., 10 points per km).
            int points = (int) (distance * 10);
            // Save reward using DAO, linking it to the Auth0 UserID.
            rewardEntity = rewardDao.saveReward(auth0UserId, points);
        } else {
            // Save an ineligible reward entry to the database.
            rewardEntity = new RewardEntity(auth0UserId, 0, "User is not eligible for a reward.");
            rewardEntity.setName("Ineligible Reward");
            rewardEntity = rewardRepository.save(rewardEntity);
        }
        return rewardEntity;
    }
    public CouponEntity getCouponInformation(String couponId) {
        Optional<CouponEntity> couponOpt = couponRepository.findById(couponId);
        if (!couponOpt.isPresent()) {
            throw new RuntimeException("Coupon not found");
        }

        CouponEntity couponEntity = couponOpt.get();
        return mapToCouponInfo(couponEntity);
    }

    private CouponEntity mapToCouponInfo(CouponEntity entity) {
        CouponEntity info = new CouponEntity();
        info.setCouponId(entity.getCouponId());
        info.setCouponType(entity.getCouponType());
        info.setCouponDesc(entity.getCouponDesc());
        info.setCouponExpiryDateAndTime(entity.getCouponExpiryDateAndTime());
        info.setCouponStatus(entity.getCouponStatus());
        info.setCouponRewardPoints(entity.getCouponRewardPoints());
        return info;
    }
}
