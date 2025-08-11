package com.example.friendo.TrendingFeature.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.friendo.TrendingFeature.Model.TrendingModel;

@Repository
public interface TrendingRepository extends JpaRepository<TrendingModel,Integer>{
}
