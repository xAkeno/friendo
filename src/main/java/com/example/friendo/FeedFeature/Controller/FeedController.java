package com.example.friendo.FeedFeature.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.friendo.FeedFeature.DTO.FeedDTO;
import com.example.friendo.FeedFeature.Model.Feed;
import com.example.friendo.FeedFeature.Service.FeedService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("api/v1/feed")
public class FeedController {
    private FeedService feedService;

    @Autowired
    public FeedController(FeedService feedService){
        this.feedService = feedService;
    }

    @PostMapping("create")
    public ResponseEntity<String> createFeed(@RequestBody Feed feed,@RequestParam("user") int id){
        System.out.println(id);
        System.out.println(List.of(feed));
        if(Optional.of(feed).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Please put a body");
        }

        if(feedService.createFeed(feed,id).isPresent()){
            return ResponseEntity.ok("Successfully created a feed");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Failed to created feed");
    }
    
    @GetMapping("public")
    public List<FeedDTO> loadAll(){
        return feedService.getPublicFeed();
    }

    @GetMapping("friend")
    public List<FeedDTO> loadFriendFeed(@RequestParam("id") int id){
        return feedService.getFriendFeed(id);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteFeed(@RequestParam("creator") int creator,@RequestParam("feedid") int feedid){
        if(feedService.deleteFeed(feedid, creator).isPresent()){
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
