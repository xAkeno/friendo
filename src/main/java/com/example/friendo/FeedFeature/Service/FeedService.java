package com.example.friendo.FeedFeature.Service;

import java.io.ObjectInputFilter.Status;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.friendo.AccountFeature.DTO.AccountDTO;
import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Repository.AccountRepository;
import com.example.friendo.FeedFeature.DTO.FeedDTO;
import com.example.friendo.FeedFeature.Model.Comment;
import com.example.friendo.FeedFeature.Model.Feed;
import com.example.friendo.FeedFeature.Model.Visibility;
import com.example.friendo.FeedFeature.Repository.FeedRepository;
import com.example.friendo.FriendFeature.Model.Friend;
import com.example.friendo.FriendFeature.Service.FriendService;

import jakarta.transaction.Transactional;

@Service
public class FeedService {
    private FeedRepository feedRepository;
    private AccountRepository accountRepository;
    private FriendService friendService;

    @Autowired
    public FeedService(FeedRepository feedRepository,AccountRepository accountRepository,FriendService friendService){
        this.feedRepository = feedRepository;
        this.accountRepository = accountRepository;
        this.friendService = friendService;
    }

    //create feed
    
    public Optional<Feed> createFeed(Feed feed,Integer id){
        if(!Optional.of(feed).isPresent() && id == null){
            return Optional.empty();
        } 
        Account acc = accountRepository.findById(id).get();
        System.out.println(feed.getVisibility() + "<=== is the feed visiblity");
        Feed newFeed = new Feed();
        newFeed.setContext(feed.getContext());
        newFeed.setCreated_at(String.valueOf(LocalTime.now()));
        newFeed.setVisibility(feed.getVisibility());
        newFeed.setAccount(acc);

        return Optional.of(feedRepository.save(newFeed));
    }

    //get all the public feed
    public List<FeedDTO> getPublicFeed(){
        List<Object[]> load = feedRepository.getAllPublicFeed();

        List<FeedDTO> newFeedDTO = new ArrayList<>();

        for(Object[] row : load){
            FeedDTO dto = new FeedDTO();

            dto.setId((Integer) row[0]);
            dto.setContext((String) row[1]);
            dto.setCreatedAt((String) row[2]); 
            dto.setVisibility(String.valueOf(row[3])); 
            dto.setAccount((Integer) row[4]);

            newFeedDTO.add(dto);
        }

        return newFeedDTO;
    }

    //get all the friend feed
    public List<FeedDTO> getFriendFeed(Integer id) {
        if (id == null) {
            return List.of();
        }
    
        List<FeedDTO> newFeed = new ArrayList<>();
    
        List<AccountDTO> friends = friendService.viewAllFriend(id);
        for (AccountDTO friend : friends) {
            System.out.println(friend.getId());
            List<Object[]> feeds = feedRepository.getFriendFeed(friend.getId());
    
            for (Object[] feed : feeds) {
                List<Object> list1 = Arrays.asList(feed);

                FeedDTO feedDTO = new FeedDTO();
                feedDTO.setId((Integer) list1.get(0));
                feedDTO.setContext((String) list1.get(1));
                feedDTO.setCreatedAt((String) list1.get(2));
                feedDTO.setVisibility((String) list1.get(3));
                feedDTO.setAccount((Integer) list1.get(4));
                newFeed.add(feedDTO);
            }
        }
    
        List<Object[]> self = feedRepository.getFriendFeed(id);
        System.out.println(id + "<SElf");
        for (Object[] obj : self) {
            List<Object> list2 = Arrays.asList(obj);

            FeedDTO feedDTO = new FeedDTO();
            feedDTO.setId((Integer) list2.get(0));
            feedDTO.setContext((String) list2.get(1));
            feedDTO.setCreatedAt((String) list2.get(2));
            feedDTO.setVisibility((String) list2.get(3));
            feedDTO.setAccount((Integer) list2.get(4));
            newFeed.add(feedDTO);
        }
    
        return newFeed;
    }
    
    //delete feed
    @Transactional
    public Optional<Feed> deleteFeed(Integer feedid,Integer creator){
        if(feedid == null && creator == null){
            return Optional.empty();
        }
        Optional<Feed> check = feedRepository.checkFeedCreator(creator,feedid);
        if(check.isEmpty()){
            return Optional.empty();
        }
        feedRepository.delete(check.get());
        return check;
    }

    //Edit feed
    @Transactional
    public Optional<Feed> editFeed(Feed feed,Integer creator,Integer feedid){
        if(feed == null && creator == null){
            return Optional.empty();
        }
        Optional<Feed> check = feedRepository.checkFeedCreator(creator,feedid);
        if(check.isEmpty()){
            return Optional.empty();
        }

        Feed newFeed = check.get();
        newFeed.setVisibility(feed.getVisibility());
        newFeed.setContext(feed.getContext());
        return Optional.of(feedRepository.save(newFeed));
    }
}
