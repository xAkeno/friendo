package com.example.friendo.AccountExtraFeature.Controller;

import java.util.Optional;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.friendo.AccountExtraFeature.Model.AccountExtraModel;
import com.example.friendo.AccountExtraFeature.Service.AccountExtraService;
import com.example.friendo.AccountFeature.DTO.AccountDTO;
import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Repository.AccountRepository;
import com.example.friendo.AccountFeature.Service.JwtService;

@RestController
@RequestMapping("/api/v1/extra")
public class AccountExtraController {
    private AccountExtraService accountExtraService;
    private AccountRepository accountRepository;
    private JwtService jwtService;
    @Autowired
    public AccountExtraController(AccountExtraService accountExtraService,JwtService jwtService,AccountRepository accountRepository){
        this.accountExtraService = accountExtraService;
        this.accountRepository = accountRepository;
        this.jwtService = jwtService;
    }
    @PostMapping(value ="/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AccountExtraModel addExtra(@RequestPart("body")AccountExtraModel accountExtraModel,@RequestPart("img")MultipartFile img,@CookieValue(name = "JWT", required = false) String jwt){
        String username = jwtService.extractUsername(jwt);
        Account account = accountRepository.findByUsername(username).get();
        if(jwt.isEmpty()){
            throw new RuntimeException("No jwt found");
        }
        return accountExtraService.registerExtra(accountExtraModel, img, account.getId());
    }
    @GetMapping("/getExtra")
    public ResponseEntity<?> getExtraAccount(@CookieValue(name = "JWT", required = false) String jwt){
        String username = jwtService.extractUsername(jwt);
        Account account = accountRepository.findByUsername(username).get();
        if(jwt.isEmpty()){
            throw new RuntimeException("No jwt found");
        }
        return ResponseEntity.ok().body(accountExtraService.getExtra(account.getId()));
    }
    @GetMapping("/getUsername")
    public ResponseEntity<String> getUsername(@CookieValue(name = "JWT", required = false) String jwt){
        System.out.println("Check cehc" + jwt);
        String username = jwtService.extractUsername(jwt);
        Account account = accountRepository.findByUsername(username).get();
        AccountDTO use = new AccountDTO();
        use.setUsername(username);
        if(jwt.isEmpty()){
            throw new RuntimeException("No jwt found");
        }
        return ResponseEntity.ok().body(account.getUsername());
    }
}
