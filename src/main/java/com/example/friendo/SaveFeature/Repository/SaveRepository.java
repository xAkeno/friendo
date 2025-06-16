package com.example.friendo.SaveFeature.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.FeedFeature.Model.Feed;
import com.example.friendo.SaveFeature.Model.SaveModel;

@Repository
public interface SaveRepository extends JpaRepository<SaveModel,Integer>{
    @Query(value = "SELECT * FROM save_model WHERE account_id = :userId",nativeQuery = true)
    public List<Object[]> findAllSaved(@Param("userId")Integer userId);

    @Query("SELECT s FROM SaveModel s WHERE s.account.id = :userId AND s.feed.id = :feedId")
    Optional<SaveModel> findSaved(@Param("userId") Integer userId, @Param("feedId") Integer feedId);

    // Optional<SaveModel> findByAccountAndFeed(Account account, Feed feed);

}
