package com.example.reward_service.dao;

import com.example.reward_service.entity.TotalRewardsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TotalRewardsRepository extends JpaRepository<TotalRewardsEntity, String> {
    // userId is the primary key
}
