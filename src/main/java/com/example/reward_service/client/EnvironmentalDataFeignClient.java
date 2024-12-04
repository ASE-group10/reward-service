package com.example.reward_service.client;


import com.example.reward_service.model.GeometryCoordinates;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "environmental-data-service", url = "http://environmental-data-service/api/v1/environmental-data")
public interface EnvironmentalDataFeignClient {

    @GetMapping("/coordinates")
    List<GeometryCoordinates> getAllCoordinates();
}