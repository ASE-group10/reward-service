package com.example.reward_service.dao;

import com.example.reward_service.entity.RewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<RewardEntity, Long> {
}
