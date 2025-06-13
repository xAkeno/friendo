package com.example.friendo.FeedFeature.Service;

import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Repository.AccountRepository;
import com.example.friendo.FeedFeature.Model.Feed;
import com.example.friendo.FeedFeature.Model.LikeFeed;
import com.example.friendo.FeedFeature.Model.Visibility;
import com.example.friendo.FeedFeature.Repository.FeedRepository;
import com.example.friendo.FeedFeature.Repository.LikeRepository;

@Service
public class LikeService {
    private LikeRepository likeRepository;
    private FeedRepository feedRepository;
    private AccountRepository accountRepository;
    
    @Autowired
    public LikeService(LikeRepository likeRepository,FeedRepository feedRepository,AccountRepository accountRepository){
        this.likeRepository = likeRepository;
        this.feedRepository = feedRepository;
        this.accountRepository = accountRepository;
    }

    public Optional<LikeFeed> likeFeed(Integer target,Integer userid){
        Feed feeds = feedRepository.findById(target).get();
        Account acc = accountRepository.findById(userid).get();

        if(feeds == null){
            return Optional.empty();
        }
        if(acc == null){
            return Optional.empty();
        }
        if(likeRepository.findLiker(target, userid).isPresent()){
            return Optional.empty();
        }
        LikeFeed newLikeFeed = new LikeFeed();
        newLikeFeed.setFeed(feeds);
        newLikeFeed.setAccount(acc);

        return Optional.of(likeRepository.save(newLikeFeed));
    }
    public String unlikeFeeed(Integer target,Integer userid){
        Feed feeds = feedRepository.findById(target)
            .orElseThrow(() -> new NoSuchElementException("Feed not found with ID : " + target));
        Account acc = accountRepository.findById(userid)
            .orElseThrow(() -> new NoSuchElementException("Account not found with ID : " + userid));

        Optional<LikeFeed> like = likeRepository.findLiker(target, userid);
        if(like.isPresent()){
            likeRepository.delete(like.get());
            return "Successfully unlike";
        }
        return null;
    }
}
