package com.example.friendo.AccountFeature.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.friendo.AccountFeature.Model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer>{
    @Query(value = "SELECT * FROM Account WHERE email = :email AND password = :password",nativeQuery=true)
    Optional<Account> findByEmailAndPassword(@Param("email") String email,@Param("password")String password);

    @Query(value = "SELECT * FROM Account WHERE email = :email",nativeQuery=true)
    Optional<Account> findByEmail(@Param("email") String email);
}
