package com.example.friendo.FeedFeature.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Repository.AccountRepository;
import com.example.friendo.AccountFeature.Service.JwtService;
import com.example.friendo.FeedFeature.DTO.FeedDTO;
import com.example.friendo.FeedFeature.Model.Feed;
import com.example.friendo.FeedFeature.Service.FeedService;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/auth/api/v1/feed")
public class FeedController {
    private FeedService feedService;
    private JwtService jwtService;
    private AccountRepository accountRepository;
    @Autowired
    public FeedController(FeedService feedService,JwtService jwtService,AccountRepository accountRepository){
        this.feedService = feedService;
        this.jwtService = jwtService;
        this.accountRepository = accountRepository;
    }
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        System.out.println("Test endpoint hit");
        return ResponseEntity.ok("Controller works");
    }

    @PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createFeed(@RequestPart("body") Feed feed,@CookieValue(name = "JWT", required = false) String jwt,@RequestPart("image")MultipartFile[] image){

        System.out.println("chec if running");
        if(Optional.of(feed).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Please put a body");
        }
        String username = jwtService.extractUsername(jwt);
        Account account = accountRepository.findByUsername(username).get();
        if(feedService.createFeed(feed,account.getId(),image).isPresent()){
            return ResponseEntity.ok("Successfully created a feed");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Failed to created feed");
    }
    
    @GetMapping("/public")
    public List<FeedDTO> loadAll(@CookieValue(name = "JWT", required = false) String jwt){
        String username = jwtService.extractUsername(jwt);
        Account account = accountRepository.findByUsername(username).get();
        System.out.println(username + "Usernammem " + account.getId());
        return feedService.getPublicFeed(account.getId());
    }

    @GetMapping("/friend")
    public List<FeedDTO> loadFriendFeed(@CookieValue(name = "JWT", required = false) String jwt){
        String username = jwtService.extractUsername(jwt);
        Account account = accountRepository.findByUsername(username).get();
        System.out.println(username + "Usernammem " + account.getId());
        return feedService.getFriendFeed(account.getId());
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteFeed(@CookieValue(name = "JWT", required = false) String jwt,@RequestParam("feedid") int feedid){
        String username = jwtService.extractUsername(jwt);
        Account account = accountRepository.findByUsername(username).get();
        if(feedService.deleteFeed(feedid, account.getId()).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted the feed");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Failed to deleted feed");
    }
    @PutMapping("edit")
    public ResponseEntity<String> editFeed(@RequestBody Feed feed,@RequestParam("creator") int creator,@RequestParam("feedid") int feedid){
        if(feedService.editFeed(feed, creator, feedid).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body("Successfully Edited");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Failed to edit");
    }
}
