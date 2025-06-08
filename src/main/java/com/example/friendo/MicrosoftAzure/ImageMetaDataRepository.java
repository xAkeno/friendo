package com.example.friendo.MicrosoftAzure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageMetaDataRepository extends JpaRepository<ImageMetaModel,Integer>{
    @Query(value = "SELECT * FROM image_meta_model WHERE feed_id = :feedId",nativeQuery=true)
    List<Object[]> findByFeedId(@Param("feedId")Integer feedId);
}
