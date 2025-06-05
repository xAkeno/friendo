package com.example.friendo.FriendFeature.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.friendo.FriendFeature.Model.Friend;

@Repository
public interface FriendRepository extends JpaRepository<Friend,Integer>{
    @Query("SELECT f FROM Friend f JOIN f.account a WHERE f.user_id = :userId AND a.id = :accountId")
    Optional<Friend> findByUserIdAndAccountId(@Param("userId") Integer userId, @Param("accountId") Integer accountId);


    @Query(value = "select * from friend where id = :id and status = 2;",nativeQuery = true)
    List<Object[]> findFriend(@Param("id")Integer id);

    @Query(value = "select * from friend where user_id = :id and status = 2",nativeQuery = true)
    List<Object[]> findReverseFriend(@Param("id") Integer id);

    @Query(value = "SELECT * FROM friend WHERE user_id = :id AND status = 0",nativeQuery = true)
    List<Object[]> findRequested(@Param("id") Integer id);
}
