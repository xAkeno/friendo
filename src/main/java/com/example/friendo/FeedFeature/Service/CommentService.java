package com.example.friendo.FeedFeature.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Repository.AccountRepository;
import com.example.friendo.FeedFeature.DTO.CommentDTO;
import com.example.friendo.FeedFeature.Model.Comment;
import com.example.friendo.FeedFeature.Model.Feed;
import com.example.friendo.FeedFeature.Repository.CommentRepository;
import com.example.friendo.FeedFeature.Repository.FeedRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private FeedRepository feedRepository;
    private AccountRepository accountRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository,FeedRepository feedRepository,AccountRepository accountRepository){
        this.commentRepository = commentRepository;
        this.feedRepository = feedRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Optional<Comment> addComment(Comment comment,Integer target,Integer userid){
        if(Optional.of(comment).isEmpty() && Optional.of(target).isEmpty()){
            return Optional.empty();
        }
        Optional<Feed> feeds = feedRepository.findById(target);
        Optional<Account> acc = accountRepository.findById(userid);

        Comment newComment = new Comment();
        newComment.setContent(comment.getContent());
        newComment.setCreated_At(String.valueOf(LocalTime.now()));
        newComment.setAccount(acc.get());
        newComment.setFeed(feeds.get());

        return Optional.of(commentRepository.save(newComment));
    }
    @Transactional
    public List<CommentDTO> viewComment(int target){
        List<CommentDTO> newCommentDTos = new ArrayList<>();

        List<Object[]> loadComment = commentRepository.getAllComment(target);

        for(Object[] row : loadComment){
            CommentDTO temp = new CommentDTO();
            temp.setContent((String)row[1]);
            temp.setCreated_At((String)row[2]);
            temp.setId((Integer) row[0]);
            temp.setUserid((Integer) row[3]);
            System.out.println( row[1] + " === "+row[1]+  row[2] + row[0]);
            System.out.println("hehe");
            newCommentDTos.add(temp);
        }
        System.out.println("hehe");
        return newCommentDTos;
    }
}
