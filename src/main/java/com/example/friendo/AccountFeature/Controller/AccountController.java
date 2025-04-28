package com.example.friendo.AccountFeature.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.friendo.AccountFeature.DTO.AccountDTO;
import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Service.AccountService;

@RestController
@RequestMapping("api/v1/account")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping("create")
    public List<Account> RegisterAccount(@RequestBody Account account){
        if(Optional.of(account).isPresent() && accountService.Register(account).isPresent()){
            return List.of(account);
        }else return List.of();
    }

    @PostMapping("login")
    public ResponseEntity<AccountDTO> LogInAccount(@RequestBody Account account){
        AccountDTO pass = accountService.Login(account).get();
        if(Optional.of(account).isPresent() && Optional.of(pass).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(pass);
        }else return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
    }
}
