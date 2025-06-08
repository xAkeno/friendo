package com.example.friendo.FriendFeature.Controller;

import java.lang.foreign.Linker.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.friendo.AccountFeature.DTO.AccountDTO;
import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Repository.AccountRepository;
import com.example.friendo.AccountFeature.Service.JwtService;
import com.example.friendo.FriendFeature.Model.Friend;
import com.example.friendo.FriendFeature.Service.FriendService;

@RestController
@RequestMapping("/api/v1/friend")
public class FriendController {
    //request friend
    private FriendService friendService;
    private AccountRepository accountRepository;
    private JwtService jwtService;
    @Autowired
    public FriendController(FriendService friendService,AccountRepository accountRepository,JwtService jwtService){
        this.friendService = friendService;
        this.accountRepository = accountRepository;
        this.jwtService = jwtService;
    }
    @GetMapping("/test")
    public String test(){
        return "This is a test";
    }
    @PostMapping("/request")
    public ResponseEntity<String> request(@RequestParam("send") String send,@CookieValue(name = "JWT", required = false) String jwt){
        System.out.println("hello tets test");
        String username = jwtService.extractUsername(jwt);
        Account account = accountRepository.findByUsername(username).get();

        System.out.println(account.getId() + "<====");
        
        Optional<?> req = friendService.request(send,account.getId());
        if(Optional.of(send).isPresent() && req.isPresent()){
            if(req.get().equals("same")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot friend self");
            }
            if(req.get().equals("already")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already requested");
            }
            return ResponseEntity.status(HttpStatus.OK).body("Successfully requested");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Failed to request");
    }

    @PostMapping("accept")   
    public ResponseEntity<String> accept(@RequestParam("send") Integer sendId,@CookieValue(name = "JWT", required = false) String jwt){
        String username = jwtService.extractUsername(jwt);
        Account account = accountRepository.findByUsername(username).get();

        Optional<?> req = friendService.accept(sendId, account.getId());
        if(req.isPresent() && Optional.of(List.of(sendId,account.getId())).isPresent()){
            if(req.get().equals("same")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot accept self");
            }
            return ResponseEntity.status(HttpStatus.OK).body("Successfully accepted");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Failed to accept");
    }
    @PostMapping("reject")   
    public ResponseEntity<String> reject(@RequestParam("send") Integer sendId,@CookieValue(name = "JWT", required = false) String jwt){
        String username = jwtService.extractUsername(jwt);
        Account account = accountRepository.findByUsername(username).get();

        Optional<?> req = friendService.reject(sendId, account.getId());
        if(req.isPresent() && Optional.of(List.of(sendId,account.getId())).isPresent()){
            if(req.get().equals("same")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot reject self");
            }
            return ResponseEntity.status(HttpStatus.OK).body("Successfully rejected");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Failed to accept");
    }
    @PostMapping("unfriend")   
    public ResponseEntity<String> unfriend(@RequestParam("send") Integer sendId,@CookieValue(name = "JWT", required = false) String jwt){
        String username = jwtService.extractUsername(jwt);
        Account account = accountRepository.findByUsername(username).get();

        Optional<?> req = friendService.unfriend(sendId, account.getId());
        if(req.isPresent() && Optional.of(List.of(sendId,account.getId())).isPresent()){
            if(req.get().equals("same")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot unfriend self");
            }
            return ResponseEntity.status(HttpStatus.OK).body("Successfully unfriended");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Failed to unfriend");
    }

    @GetMapping("view")
    public List<AccountDTO> view(@CookieValue(name = "JWT", required = false) String jwt){
        String username = jwtService.extractUsername(jwt);
        Account account = accountRepository.findByUsername(username).get();
        return friendService.viewAllFriend(account.getId());
    }

    @GetMapping("viewRequest")
    public List<AccountDTO> req(@CookieValue(name = "JWT", required = false) String jwt){
        String username = jwtService.extractUsername(jwt);
        Account account = accountRepository.findByUsername(username).get();
        return friendService.viewRequest(account.getId());
    }
}
