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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.friendo.AccountFeature.DTO.AccountDTO;
import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.FriendFeature.Model.Friend;
import com.example.friendo.FriendFeature.Service.FriendService;

@RestController
@RequestMapping("api/v1/friend")
public class FriendController {
    //request friend
    private FriendService friendService;

    @Autowired
    public FriendController(FriendService friendService){
        this.friendService = friendService;
    }
    @PostMapping("request")
    public ResponseEntity<String> request(@RequestParam("send") String sendId, @RequestParam("id") Integer id){
        Optional<Friend> req = friendService.request(sendId,id);
        if(Optional.of(sendId).isPresent() && req.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body("Successfully requested");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Failed to request");
    }

    @PostMapping("accept")   
    public ResponseEntity<String> accept(@RequestParam("send") Integer sendId, @RequestParam("id") Integer id){
        Optional<Friend> req = friendService.accept(sendId, id);
        if(req.isPresent() && Optional.of(List.of(sendId,id)).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body("Successfully accepted");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Failed to accept");
    }
    @PostMapping("reject")   
    public ResponseEntity<String> reject(@RequestParam("send") Integer sendId, @RequestParam("id") Integer id){
        Optional<Friend> req = friendService.reject(sendId, id);
        if(req.isPresent() && Optional.of(List.of(sendId,id)).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body("Successfully rejected");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Failed to accept");
    }
    @PostMapping("unfriend")   
    public ResponseEntity<String> unfriend(@RequestParam("send") Integer sendId, @RequestParam("id") Integer id){
        Optional<Friend> req = friendService.unfriend(sendId, id);
        if(req.isPresent() && Optional.of(List.of(sendId,id)).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body("Successfully unfriended");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Failed to unfriend");
    }

    @GetMapping("view")
    public List<AccountDTO> view(@RequestParam("id")Integer id){
        return friendService.viewAllFriend(id);
    }

    @GetMapping("viewRequest")
    public List<AccountDTO> req(@RequestParam("id") Integer id){
        return friendService.viewRequest(id);
    }
}
