package com.example.reward_service.dao;

import com.example.reward_service.model.RouteDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<RouteDetails, Long> {}
