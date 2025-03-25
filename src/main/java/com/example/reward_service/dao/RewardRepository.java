package com.example.reward_service.dao;

import com.example.reward_service.entity.RewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RewardRepository extends JpaRepository<RewardEntity, Long> {
    List<RewardEntity> findByUserId(String userId);
}
