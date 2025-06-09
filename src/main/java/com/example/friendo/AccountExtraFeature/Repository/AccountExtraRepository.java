package com.example.friendo.AccountExtraFeature.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.friendo.AccountExtraFeature.Model.AccountExtraModel;

@Repository
public interface AccountExtraRepository extends JpaRepository<AccountExtraModel,Integer>{
    @Query(value = "SELECT * FROM account_extra WHERE account_id = :id",nativeQuery = true)
    Optional<AccountExtraModel> findByAccount(@Param("id")Integer id);
}
