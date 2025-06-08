package com.example.friendo.FeedFeature.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Repository.AccountRepository;
import com.example.friendo.AccountFeature.Service.JwtService;
import com.example.friendo.FeedFeature.Model.Feed;
import com.example.friendo.FeedFeature.Model.LikeFeed;
import com.example.friendo.FeedFeature.Service.LikeService;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/like")
public class LikeController {

    private LikeService likeService;
    private AccountRepository accountRepository;
    private JwtService jwtService;
    @Autowired
    public LikeController(LikeService likeService,AccountRepository accountRepository,JwtService jwtService){
        this.likeService = likeService;
        this.accountRepository = accountRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("add")
    public ResponseEntity<String> likeFeed(@CookieValue(name = "JWT", required = false) String jwt,@RequestParam("target") Integer id){
        String username = jwtService.extractUsername(jwt);
        Account account = accountRepository.findByUsername(username).get();
        if(likeService.likeFeed( id, account.getId()).isPresent()){
            return ResponseEntity.ok("Successfully liked");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Failed to created feed");
    }
    
}
