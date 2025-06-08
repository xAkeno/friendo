package com.example.friendo.FeedFeature.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.friendo.FeedFeature.Model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer>{

    @Query(value = "SELECT * FROM comment WHERE feed_id = :target",nativeQuery = true)
    List<Object[]> getAllComment(@Param("target") Integer id);
}
