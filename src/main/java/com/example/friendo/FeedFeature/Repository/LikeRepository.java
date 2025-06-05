package com.example.friendo.FeedFeature.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.friendo.FeedFeature.Model.LikeFeed;

@Repository
public interface LikeRepository extends JpaRepository<LikeFeed,Integer>{

}
