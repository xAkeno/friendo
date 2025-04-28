package com.example.friendo.FeedFeature.Service;

import java.time.LocalTime;
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

    public Optional<LikeFeed> likeFeed(LikeFeed likeFeed,Integer id,Integer userid){
        if(!Optional.of(likeFeed).isPresent()){
            return Optional.empty();
        } 
        Feed feeds = feedRepository.findById(id).get();
        Account acc = accountRepository.findById(userid).get();
        
        LikeFeed newLikeFeed = new LikeFeed();
        newLikeFeed.setFeed(feeds);
        newLikeFeed.setAccount(acc);

        return Optional.of(likeRepository.save(newLikeFeed));
    }
}
