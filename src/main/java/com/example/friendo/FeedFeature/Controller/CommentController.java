package com.example.friendo.FeedFeature.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.friendo.FeedFeature.DTO.CommentDTO;
import com.example.friendo.FeedFeature.Model.Comment;
import com.example.friendo.FeedFeature.Service.CommentService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/comment")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("create")
    public ResponseEntity<String> createComment(@RequestBody Comment comment,@RequestParam("feedId") Integer id,@RequestParam("userid") Integer userid){
        if(Optional.of(comment).isEmpty() || Optional.of(id).isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("Please complete the comment/feed if");
        }
        if(commentService.addComment(comment,id,userid).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body("Successfully added comment");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Failed to comment");
    }
    
    @GetMapping("viewComment")
    public ResponseEntity<List<CommentDTO>> viewComment(@RequestParam("target")int target){
        if(Optional.of(target).isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        System.out.println("ss");
        List<CommentDTO> x = commentService.viewComment(target);
        if(x.isEmpty()){
            ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(x);

    }
}
