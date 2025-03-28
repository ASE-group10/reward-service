package com.example.reward_service.dao;

import com.example.reward_service.entity.RewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardRepository extends JpaRepository<RewardEntity, Long> {
    List<RewardEntity> findByUserId(String userId);
}
