package com.example.reward_service.dao;

import com.example.reward_service.entity.TotalRewardsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<TotalRewardsEntity, String> {
    // Additional custom queries, if needed
}
