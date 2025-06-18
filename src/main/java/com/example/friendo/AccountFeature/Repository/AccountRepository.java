package com.example.friendo.AccountFeature.Repository;

import java.util.List;
import java.util.Optional;

import org.junit.runners.Parameterized.Parameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.Websocket.Model.Status;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer>{
    @Query(value = "SELECT * FROM Account WHERE email = :email AND password = :password",nativeQuery=true)
    Optional<Account> findByEmailAndPassword(@Param("email") String email,@Param("password")String password);

    @Query(value = "SELECT * FROM Account WHERE email = :email",nativeQuery=true)
    Optional<Account> findByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM Account WHERE username = :username",nativeQuery=true)
    Optional<Account> findByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM Account WHERE verification_code = :verification_code",nativeQuery=true)
    Optional<Account>  findByVerificationCode(String verification_code);

    List<Account> findAllByStatus(Status status);
}
