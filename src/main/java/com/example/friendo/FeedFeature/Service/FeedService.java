package com.example.friendo.FeedFeature.Service;

import java.io.ObjectInputFilter.Status;
import java.time.LocalDateTime;
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
import com.example.friendo.FeedFeature.DTO.LikeDTO;
import com.example.friendo.FeedFeature.Model.Comment;
import com.example.friendo.FeedFeature.Model.Feed;
import com.example.friendo.FeedFeature.Model.LikeFeed;
import com.example.friendo.FeedFeature.Model.Visibility;
import com.example.friendo.FeedFeature.Repository.CommentRepository;
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
    private CommentRepository commentRepository;
    @Autowired
    public FeedService(FeedRepository feedRepository,
            AccountRepository accountRepository,
            FriendService friendService,
            LikeRepository likeRepository,
            imageMetaDataService imageMetaDataServices,
            ImageMetaDataRepository imageMetaDataRepository,
            CommentRepository commentRepository)
    {
        this.feedRepository = feedRepository;
        this.accountRepository = accountRepository;
        this.friendService = friendService;
        this.likeRepository = likeRepository;
        this.imageMetaDataServices = imageMetaDataServices;
        this.imageMetaDataRepository = imageMetaDataRepository;
        this.commentRepository = commentRepository;
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
            newFeed.setCreated_at(LocalDateTime.now());
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
            List<Object[]> allWhoLike = likeRepository.getAllWhoLike((Integer) row[0]);

            dto.setId((Integer) row[0]);
            dto.setContext((String) row[1]);
            dto.setCreatedAt(String.valueOf(row[2])); 
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
            // dto.setLikeFeed(likes.get());
            dto.setLike(likes.isPresent() ? true : false);

            
            // dto.setLikeFeed(allWhoLike.get());
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
            
            //add self to friened to view also own post
            Account self = accountRepository.findById(id).orElse(null);
            if (self != null) {
                AccountDTO selfDto = new AccountDTO();
                selfDto.setId(self.getId());
                selfDto.setEmail(self.getEmail());
                selfDto.setFirstname(self.getFirstname());
                selfDto.setLastname(self.getLastname());
                selfDto.setUsername(self.getUsername());
                friends.add(selfDto);
            }
            //loop to each friend to geet their post
            for (AccountDTO friend : friends) {
                List<Object[]> feeds = feedRepository.getFriendFeed(friend.getId());
                for (Object[] feed : feeds) {
                    Integer feedId = (Integer) feed[0];
                    //check if the user is already like
                    Optional<LikeFeed> likes = likeRepository.findLiker(feedId, id);
                    //get all those who like the feed
                    List<Object[]> allWhoLike = likeRepository.getAllWhoLike(feedId);
                    if (addedFeedIds.add(feedId)) { // Only add if not already present
                        FeedDTO feedDTO = new FeedDTO();
                        feedDTO.setId(feedId);
                        feedDTO.setContext((String) feed[1]);
                        feedDTO.setCreatedAt(String.valueOf(feed[2]));
                        feedDTO.setVisibility((String) feed[3]);
                        List<Comment> comments = new ArrayList<>();
                        List<Object[]> loadedComment = commentRepository.getAllComment(feedId);
                        //loop to get all the comment
                        for(Object[] commentRow : loadedComment){
                            Comment comment = new Comment();
                            comment.setId((Integer)commentRow[0]);
                            comment.setContent((String)commentRow[1]);
                            comment.setCreated_At((String)commentRow[2]);
                            Account creatorComment = accountRepository.findById((Integer) commentRow[3]).get();
                            if(Optional.of(creatorComment).isPresent()){
                                Account account = new Account();
                                account.setFirstname(creatorComment.getFirstname());
                                account.setLastname(creatorComment.getLastname());
                                account.setEmail(creatorComment.getEmail());
                                account.setId(creatorComment.getId());
                                account.setUsername(creatorComment.getUsername());
                                comment.setAccount(account);
                            }
                            comments.add(comment);
                        }
                        feedDTO.setComments(comments);
                        //get the creator
                        Account account = accountRepository.findById((Integer) feed[4]).get();
                        AccountDTO Passaccount = new AccountDTO();
                            Passaccount.setEmail(account.getEmail());
                            Passaccount.setFirstname(account.getFirstname());
                            Passaccount.setLastname(account.getLastname());
                            Passaccount.setId(account.getId());
                            Passaccount.setUsername(account.getUsername());
                        // get the img of the post and put it on the list
                        List<ImageMetaModel> imageList = new ArrayList<>();
                        List<Object[]> loadedImage = imageMetaDataRepository.findByFeedId(feedId);
                        if(loadedImage.isEmpty()){
                            System.out.println("No loaded image fround with id name " + feedId);
                        }
                        //load all image and put it on a llist
                        for(Object[] imgRow : loadedImage){
                            ImageMetaModel image = new ImageMetaModel();
                            image.setId((Integer)imgRow[0]);
                            image.setImageUrl((String)imgRow[2]);
                            imageList.add(image);
                        }
                        feedDTO.setImageMetaModels(imageList);
                        feedDTO.setAccount(Passaccount);

                        //get All thee liker and check if they user alreaedy like it
                        feedDTO.setLike(likes.isPresent() ? true : false);

                        // //load all the likere into object
                        List<LikeDTO> likeFeeds = new ArrayList<>();
                        // List<AccountDTO> accountDTOs = new ArrayList<>();
                        for(Object[] rowx : allWhoLike){
                            System.out.println(rowx[0] + "<<<" + rowx[1] + "<>>>" +rowx[2]);
                            Account userLiker = accountRepository.findById((Integer) rowx[1]).get();
                            AccountDTO userLikerDto = new AccountDTO();
                            userLikerDto.setEmail(userLiker.getEmail());
                            userLikerDto.setFirstname(userLiker.getFirstname());
                            userLikerDto.setLastname(userLiker.getLastname());
                            userLikerDto.setId(userLiker.getId());
                            LikeDTO likeDTO = new LikeDTO();
                            likeDTO.setAccount(userLikerDto);
                            likeFeeds.add(likeDTO);
                        }
                        feedDTO.setLikeFeed(likeFeeds);
                        //     feedDTO.setLikeFeed(allWhoLike.get());
                        newFeed.add(feedDTO);
                    }
                }
            }
            //get all the post that is public 
            List<Object[]> load = feedRepository.getAllPublicFeed();
            //loop to get all result into list
            for(Object[] row : load){
                Integer feedId = (Integer) row[0];
                if (addedFeedIds.add(feedId)) {//only get the object that is already listed
                    //check if the user is already like
                    Optional<LikeFeed> likes = likeRepository.findLiker(feedId, id);
                    //get all those who like the feed
                    List<Object[]> allWhoLike = likeRepository.getAllWhoLike(feedId);
                    FeedDTO dto = new FeedDTO();
                    dto.setId(feedId);
                    dto.setContext((String) row[1]);
                    dto.setCreatedAt(String.valueOf(row[2]));
                    dto.setVisibility((String) row[3]);

                    //loop to get all the comment and their account
                    List<Comment> comments = new ArrayList<>();
                    List<Object[]> loadedComment = commentRepository.getAllComment(feedId);
                    for(Object[] commentRow : loadedComment){
                        Comment comment = new Comment();
                        comment.setId((Integer)commentRow[0]);
                        comment.setContent((String)commentRow[1]);
                        comment.setCreated_At((String)commentRow[2]);
                        Account creatorComment = accountRepository.findById((Integer) commentRow[3]).get();
                        if(Optional.of(creatorComment).isPresent()){
                            Account account = new Account();
                            account.setFirstname(creatorComment.getFirstname());
                            account.setLastname(creatorComment.getLastname());
                            account.setEmail(creatorComment.getEmail());
                            account.setId(creatorComment.getId());
                            account.setUsername(creatorComment.getUsername());
                            comment.setAccount(account);
                        }
                        comments.add(comment);
                    }
                    dto.setComments(comments);
                    //get the creator
                    Account account = accountRepository.findById((Integer) row[4]).get();
                    AccountDTO Passaccount = new AccountDTO();
                        Passaccount.setEmail(account.getEmail());
                        Passaccount.setFirstname(account.getFirstname());
                        Passaccount.setLastname(account.getLastname());
                        Passaccount.setId(account.getId());
                        Passaccount.setUsername(account.getUsername());
                    List<ImageMetaModel> imageList = new ArrayList<>();
                    List<Object[]> loadedImage = imageMetaDataRepository.findByFeedId(feedId);

                    // get the img of the post and put it on the list
                    for(Object[] imgRow : loadedImage){
                        ImageMetaModel image = new ImageMetaModel();
                        image.setId((Integer)imgRow[0]);
                        image.setImageUrl((String)imgRow[2]);
                        imageList.add(image);
                    }
                    dto.setImageMetaModels(imageList);
                    dto.setAccount(Passaccount);

                    dto.setLike(likes.isPresent() ? true : false);

                    // load all the likere into object
                    List<LikeDTO> likeFeeds = new ArrayList<>();
                    // List<AccountDTO> accountDTOs = new ArrayList<>();
                    for(Object[] rowz : allWhoLike){
                        System.out.println(rowz[0] + "<<<" + rowz[1] + "<>>>" +rowz[2]);
                        Account userLiker = accountRepository.findById((Integer) rowz[1]).get();
                        AccountDTO userLikerDto = new AccountDTO();
                        userLikerDto.setEmail(userLiker.getEmail());
                        userLikerDto.setFirstname(userLiker.getFirstname());
                        userLikerDto.setLastname(userLiker.getLastname());
                        userLikerDto.setId(userLiker.getId());


                        LikeDTO likeDTO = new LikeDTO();
                        likeDTO.setAccount(userLikerDto);
                        likeFeeds.add(likeDTO);
                    }
                    dto.setLikeFeed(likeFeeds);
                    // dto.setLikeFeed(allWhoLike.get());
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
