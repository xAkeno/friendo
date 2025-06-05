package com.example.friendo.FeedFeature.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.friendo.FeedFeature.Model.LikeFeed;

@Repository
public interface LikeRepository extends JpaRepository<LikeFeed,Integer>{
    @Query(value = "select * from like_feed where account_id = :userid and feed_id = :target",nativeQuery = true)
    Optional<LikeFeed> findLiker(@Param("target") Integer target, @Param("userid") Integer userid);
}
