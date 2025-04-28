package com.example.friendo.FeedFeature.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.friendo.FeedFeature.Model.Visibility;

import com.example.friendo.FeedFeature.Model.Feed;
@Repository
public interface FeedRepository extends JpaRepository<Feed,Integer>{

    @Query(value = "SELECT * FROM feed WHERE visibility = 'PUBLIC' ORDER BY id DESC",nativeQuery = true)
    List<Object[]> getAllPublicFeed();

    // @Query(value = "SELECT * FROM feed INNER JOIN friend JOIN account_friend WHERE (account_friend.account_id = :id AND friend.user_id) AND friend.status = 1 AND feed.visibility = 1;",nativeQuery = true)
    // List<Object[]> getAllFriendFeed(@Param("id")Integer id);

    @Query(value = "SELECT * FROM feed WHERE account_id = :id",nativeQuery = true)
    List<Object[]> getFriendFeed(@Param("id") Integer id);

    @Query(value = "SELECT * FROM feed WHERE account_id = :creator AND id = :feedId",nativeQuery = true)
    Optional<Feed> checkFeedCreator(@Param("creator") Integer creator,@Param("feedId") Integer feedid);
}



