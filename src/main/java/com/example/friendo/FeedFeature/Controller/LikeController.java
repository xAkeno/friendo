package com.example.friendo.FeedFeature.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.friendo.FeedFeature.Model.Feed;
import com.example.friendo.FeedFeature.Model.LikeFeed;
import com.example.friendo.FeedFeature.Service.LikeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/like")
public class LikeController {

    private LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService){
        this.likeService = likeService;
    }

    @PostMapping("add")
    public ResponseEntity<String> likeFeed(@RequestBody LikeFeed like,@RequestParam("user") Integer id,@RequestParam("userid") Integer userid){
        if(Optional.of(like).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Please put a body");
        }

        if(likeService.likeFeed(like, id, userid).isPresent()){
            return ResponseEntity.ok("Successfully liked");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Failed to created feed");
    }
    
}
