package com.example.friendo.TrendingFeature.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.friendo.TrendingFeature.Service.TrendingService;
import com.example.friendo.TrendingFeature.TrendingDTO.DTO;

@RestController
@RequestMapping("api/v1/trending")
public class TrendingController {
    private TrendingService trendingService;

    @Autowired
    public TrendingController(TrendingService trendingService){
        this.trendingService = trendingService;
    }
    @GetMapping("trend")
    public List<DTO> getTrend(){
        return trendingService.getTreding();
    }
}
