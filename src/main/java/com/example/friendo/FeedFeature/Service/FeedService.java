package com.example.friendo.FeedFeature.Service;

import java.io.ObjectInputFilter.Status;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.friendo.AccountFeature.DTO.AccountDTO;
import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Repository.AccountRepository;
import com.example.friendo.FeedFeature.DTO.FeedDTO;
import com.example.friendo.FeedFeature.Model.Comment;
import com.example.friendo.FeedFeature.Model.Feed;
import com.example.friendo.FeedFeature.Model.LikeFeed;
import com.example.friendo.FeedFeature.Model.Visibility;
import com.example.friendo.FeedFeature.Repository.FeedRepository;
import com.example.friendo.FeedFeature.Repository.LikeRepository;
import com.example.friendo.FriendFeature.Model.Friend;
import com.example.friendo.FriendFeature.Service.FriendService;
import com.example.friendo.MicrosoftAzure.ImageMetaDataRepository;
import com.example.friendo.MicrosoftAzure.ImageMetaModel;
import com.example.friendo.MicrosoftAzure.imageMetaDataService;

import jakarta.transaction.Transactional;

@Service
public class FeedService {
    private FeedRepository feedRepository;
    private AccountRepository accountRepository;
    private FriendService friendService;
    private LikeRepository likeRepository;
    private imageMetaDataService imageMetaDataServices;
    private ImageMetaDataRepository imageMetaDataRepository;
    @Autowired
    public FeedService(FeedRepository feedRepository,
            AccountRepository accountRepository,
            FriendService friendService,
            LikeRepository likeRepository,
            imageMetaDataService imageMetaDataServices,
            ImageMetaDataRepository imageMetaDataRepository)
    {
        this.feedRepository = feedRepository;
        this.accountRepository = accountRepository;
        this.friendService = friendService;
        this.likeRepository = likeRepository;
        this.imageMetaDataServices = imageMetaDataServices;
        this.imageMetaDataRepository = imageMetaDataRepository;
    }

    //create feed
    
    public Optional<Feed> createFeed(Feed feed,Integer id,MultipartFile[] image){
        if(!Optional.of(feed).isPresent() && id == null){
            return Optional.empty();
        } 
        try {
            System.out.println(id + "<===========================");
            Account acc = accountRepository.findById(id).get();
            System.out.println(feed.getVisibility() + "<=== is the feed visiblity");
            Feed newFeed = new Feed();
            newFeed.setContext(feed.getContext());
            newFeed.setCreated_at(String.valueOf(LocalTime.now()));
            newFeed.setVisibility(feed.getVisibility());
            newFeed.setAccount(acc);
            Feed savedFeed = feedRepository.save(newFeed);

            imageMetaDataServices.uploadImageWithCaption(image, savedFeed);
            return Optional.of(savedFeed);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    //get all the public feed
    public List<FeedDTO> getPublicFeed(Integer userid){
        List<Object[]> load = feedRepository.getAllPublicFeed();
        List<FeedDTO> newFeedDTO = new ArrayList<>();

        for(Object[] row : load){
            FeedDTO dto = new FeedDTO();
            Optional<LikeFeed> likes = likeRepository.findLiker((Integer) row[0], userid);

            dto.setId((Integer) row[0]);
            dto.setContext((String) row[1]);
            dto.setCreatedAt((String) row[2]); 
            dto.setVisibility(String.valueOf(row[3])); 
            Account account = accountRepository.findById((Integer) row[4]).get();
            AccountDTO Passaccount = new AccountDTO();
                Passaccount.setEmail(account.getEmail());
                Passaccount.setFirstname(account.getFirstname());
                Passaccount.setLastname(account.getLastname());
                Passaccount.setId(account.getId());
                Passaccount.setUsername(account.getUsername());
            dto.setAccount(Passaccount);
            List<ImageMetaModel> imageList = new ArrayList<>();
            List<Object[]> loadedImage = imageMetaDataRepository.findByFeedId((Integer) row[0]);

            for(Object[] imgRow : loadedImage){
                ImageMetaModel image = new ImageMetaModel();
                image.setId((Integer)imgRow[0]);
                image.setImageUrl((String)imgRow[2]);
                imageList.add(image);
            }
            dto.setImageMetaModels(imageList);
            dto.setLike(likes.isPresent() ? true : false);

            // dto.setImageMetaModels();
            newFeedDTO.add(dto);
        }

        return newFeedDTO;
    }

    //get all the friend feed
    public List<FeedDTO> getFriendFeed(Integer id) {
        try {
            List<FeedDTO> newFeed = new ArrayList<>();
            Set<Integer> addedFeedIds = new HashSet<>();
            List<AccountDTO> friends = friendService.viewAllFriend(id);
            for (AccountDTO friend : friends) {
                System.out.println(friend.getId());
                List<Object[]> feeds = feedRepository.getFriendFeed(friend.getId());
        
                for (Object[] feed : feeds) {
                    Integer feedId = (Integer) feed[0];
                    if (addedFeedIds.add(feedId)) { // Only add if not already present
                        FeedDTO feedDTO = new FeedDTO();
                        feedDTO.setId(feedId);
                        feedDTO.setContext((String) feed[1]);
                        feedDTO.setCreatedAt((String) feed[2]);
                        feedDTO.setVisibility((String) feed[3]);
                        Account account = accountRepository.findById((Integer) feed[4]).get();
                        AccountDTO Passaccount = new AccountDTO();
                            Passaccount.setEmail(account.getEmail());
                            Passaccount.setFirstname(account.getFirstname());
                            Passaccount.setLastname(account.getLastname());
                            Passaccount.setId(account.getId());
                            Passaccount.setUsername(account.getUsername());

                        List<ImageMetaModel> imageList = new ArrayList<>();
                        List<Object[]> loadedImage = imageMetaDataRepository.findByFeedId(feedId);
                        if(loadedImage.isEmpty()){
                            System.out.println("No loaded image fround with id name " + feedId);
                        }
                        for(Object[] imgRow : loadedImage){
                            ImageMetaModel image = new ImageMetaModel();
                            image.setId((Integer)imgRow[0]);
                            image.setImageUrl((String)imgRow[2]);
                            imageList.add(image);
                        }
                        feedDTO.setImageMetaModels(imageList);
                        feedDTO.setAccount(Passaccount);
                        newFeed.add(feedDTO);
                    }
                }
            }
        
            List<Object[]> load = feedRepository.getAllPublicFeed();
            for(Object[] row : load){
                Integer feedId = (Integer) row[0];
                if (addedFeedIds.add(feedId)) {
                    FeedDTO dto = new FeedDTO();
                    dto.setId(feedId);
                    dto.setContext((String) row[1]);
                    dto.setCreatedAt((String) row[2]);
                    dto.setVisibility((String) row[3]);
                    Account account = accountRepository.findById((Integer) row[4]).get();
                    AccountDTO Passaccount = new AccountDTO();
                        Passaccount.setEmail(account.getEmail());
                        Passaccount.setFirstname(account.getFirstname());
                        Passaccount.setLastname(account.getLastname());
                        Passaccount.setId(account.getId());
                        Passaccount.setUsername(account.getUsername());
                    List<ImageMetaModel> imageList = new ArrayList<>();
                    List<Object[]> loadedImage = imageMetaDataRepository.findByFeedId(feedId);

                    for(Object[] imgRow : loadedImage){
                        ImageMetaModel image = new ImageMetaModel();
                        image.setId((Integer)imgRow[0]);
                        image.setImageUrl((String)imgRow[2]);
                        imageList.add(image);
                    }
                    dto.setImageMetaModels(imageList);
                    dto.setAccount(Passaccount);
                    newFeed.add(dto);
                }
            }
            return newFeed;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
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
