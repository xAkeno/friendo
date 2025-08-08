package com.example.friendo.FeedFeature.Service;

import java.io.ObjectInputFilter.Status;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.friendo.AccountExtraFeature.Repository.AccountExtraRepository;
import com.example.friendo.AccountFeature.DTO.AccountDTO;
import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Repository.AccountRepository;
import com.example.friendo.FeedFeature.DTO.CommentDTO;
import com.example.friendo.FeedFeature.DTO.FeedDTO;
import com.example.friendo.FeedFeature.DTO.LikeDTO;
import com.example.friendo.FeedFeature.Model.Comment;
import com.example.friendo.FeedFeature.Model.Feed;
import com.example.friendo.FeedFeature.Model.LikeFeed;
import com.example.friendo.FeedFeature.Model.Visibility;
import com.example.friendo.FeedFeature.Repository.CommentRepository;
import com.example.friendo.FeedFeature.Repository.FeedRepository;
import com.example.friendo.FeedFeature.Repository.LikeRepository;
import com.example.friendo.FeedFeature.Utils.FeedUtils;
import com.example.friendo.FriendFeature.Model.Friend;
import com.example.friendo.FriendFeature.Service.FriendService;
import com.example.friendo.MicrosoftAzure.ImageMetaDataRepository;
import com.example.friendo.MicrosoftAzure.ImageMetaModel;
import com.example.friendo.MicrosoftAzure.imageMetaDataService;
import com.example.friendo.SaveFeature.Model.SaveModel;
import com.example.friendo.SaveFeature.Repository.SaveRepository;

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
    private AccountExtraRepository accountExtraRepository;
    private SaveRepository saveRepository;
    private FeedUtils feedUtils;
    @Autowired
    public FeedService(FeedRepository feedRepository,
            AccountRepository accountRepository,
            FriendService friendService,
            LikeRepository likeRepository,
            imageMetaDataService imageMetaDataServices,
            ImageMetaDataRepository imageMetaDataRepository,
            CommentRepository commentRepository,
            AccountExtraRepository accountExtraRepository,
            SaveRepository saveRepository,
            FeedUtils feedUtils)
    {
        this.feedRepository = feedRepository;
        this.accountRepository = accountRepository;
        this.friendService = friendService;
        this.likeRepository = likeRepository;
        this.imageMetaDataServices = imageMetaDataServices;
        this.imageMetaDataRepository = imageMetaDataRepository;
        this.commentRepository = commentRepository;
        this.accountExtraRepository = accountExtraRepository;
        this.saveRepository = saveRepository;
        this.feedUtils = feedUtils;
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
    @Transactional
    public List<FeedDTO> getLimitFeed(Integer id,int[] viewed){
        try {
            List<FeedDTO> newFeed = new ArrayList<>();
            Set<Integer> addedFeedIds = new HashSet<>();
            Set<Integer> viewedSet = (viewed != null && viewed.length > 0)
                ? Arrays.stream(viewed).boxed().collect(Collectors.toSet())
                : new HashSet<>();


            List<AccountDTO> friends = friendService.viewAllFriend(id);

            // Add self as a "friend" to see own posts
            accountRepository.findById(id).ifPresent(account -> {
                AccountDTO self = new AccountDTO();
                self.setId(account.getId());
                self.setEmail(account.getEmail());
                self.setFirstname(account.getFirstname());
                self.setLastname(account.getLastname());
                self.setUsername(account.getUsername());
                friends.add(self);
            });

            // Load friend feeds
            for (AccountDTO friend : friends) {
                if (newFeed.size() >= 3) break;

                List<Object[]> feeds = feedRepository.getFriendFeed(friend.getId());
                for (Object[] feed : feeds) {
                    Integer feedId = (Integer) feed[0];

                    if (viewedSet.contains(feedId) || addedFeedIds.contains(feedId)) continue;

                    FeedDTO feedDTO = buildFeedDTO(feedId, feed, id);
                    if (feedDTO != null) {
                        newFeed.add(feedDTO);
                        addedFeedIds.add(feedId);
                    }

                    if (newFeed.size() >= 3) break;
                }
            }

            // Load public feeds only if still not enough
            if (newFeed.size() < 3) {
                List<Object[]> publicFeeds = feedRepository.getAllPublicFeed();
                for (Object[] feed : publicFeeds) {
                    Integer feedId = (Integer) feed[0];

                    if (viewedSet.contains(feedId) || addedFeedIds.contains(feedId)) continue;

                    FeedDTO feedDTO = buildFeedDTO(feedId, feed, id);
                    if (feedDTO != null) {
                        newFeed.add(feedDTO);
                        addedFeedIds.add(feedId);
                    }

                    if (newFeed.size() >= 3) break;
                }
            }

            return newFeed;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private FeedDTO buildFeedDTO(Integer feedId, Object[] feed, Integer viewerId) {
        try {
            FeedDTO feedDTO = new FeedDTO();
            feedDTO.setId(feedId);
            feedDTO.setContext((String) feed[1]);
            feedDTO.setCreatedAt(String.valueOf(feed[2]));
            feedDTO.setVisibility((String) feed[3]);

            // Load creator
            Integer creatorId = (Integer) feed[4];
            Account creator = accountRepository.findById(creatorId).orElse(null);
            if (creator == null) return null;

            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setId(creator.getId());
            accountDTO.setUsername(creator.getUsername());
            accountDTO.setFirstname(creator.getFirstname());
            accountDTO.setLastname(creator.getLastname());
            accountDTO.setEmail(creator.getEmail());
            feedDTO.setAccount(accountDTO);

            if (creatorId.equals(viewerId)) {
                feedDTO.set_Owner(true);
            }

            // Load profile image
            accountExtraRepository.findByAccount(creatorId).ifPresent(extra -> {
                if (extra.getProfileImg() != null) {
                    feedDTO.setProfileImg(extra.getProfileImg());
                }
            });

            // Likes
            feedDTO.setLike(likeRepository.findLiker(feedId, viewerId).isPresent());

            List<Object[]> allWhoLike = likeRepository.getAllWhoLike(feedId);
            List<LikeDTO> likeFeeds = new ArrayList<>();
            for (Object[] row : allWhoLike) {
                Integer likerId = (Integer) row[1];
                Account liker = accountRepository.findById(likerId).orElse(null);
                if (liker == null) continue;

                AccountDTO likerDTO = new AccountDTO();
                likerDTO.setId(liker.getId());
                likerDTO.setFirstname(liker.getFirstname());
                likerDTO.setLastname(liker.getLastname());
                likerDTO.setEmail(liker.getEmail());

                LikeDTO likeDTO = new LikeDTO();
                likeDTO.setAccount(likerDTO);
                likeFeeds.add(likeDTO);
            }
            feedDTO.setLikeFeed(likeFeeds);

            // Comments
            List<CommentDTO> comments = new ArrayList<>();
            List<Object[]> loadedComment = commentRepository.getAllComment(feedId);
            for (Object[] row : loadedComment) {
                CommentDTO comment = new CommentDTO();
                comment.setId((Integer) row[0]);
                comment.setContent((String) row[1]);
                comment.setCreated_At(String.valueOf(row[2]));

                Integer commentCreatorId = (Integer) row[3];
                Account commentAccount = accountRepository.findById(commentCreatorId).orElse(null);
                if (commentAccount == null) continue;

                Account account = new Account();
                account.setId(commentAccount.getId());
                account.setFirstname(commentAccount.getFirstname());
                account.setLastname(commentAccount.getLastname());
                account.setUsername(commentAccount.getUsername());
                comment.setAccount(account);

                accountExtraRepository.findByAccount(commentCreatorId).ifPresent(extra -> {
                    if (extra.getProfileImg() != null) {
                        comment.setProfileImgUser(extra.getProfileImg());
                    }
                });

                comments.add(comment);
            }
            feedDTO.setComments(comments);

            // Images
            List<ImageMetaModel> images = new ArrayList<>();
            List<Object[]> loadedImage = imageMetaDataRepository.findByFeedId(feedId);
            for (Object[] row : loadedImage) {
                ImageMetaModel image = new ImageMetaModel();
                image.setId((Integer) row[0]);
                image.setImageUrl((String) row[2]);
                images.add(image);
            }
            feedDTO.setImageMetaModels(images);

            // Saved check
            if (saveRepository.findSaved(viewerId, feedId).isPresent()) {
                feedDTO.set_Save(true);
            }

            return feedDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //get all the friend feed
    @Transactional
    public List<FeedDTO> getFriendFeed(Integer id,int viewed[]) {
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
                        List<CommentDTO> comments = new ArrayList<>();
                        List<Object[]> loadedComment = commentRepository.getAllComment(feedId);
                        //loop to get all the comment
                        for(Object[] commentRow : loadedComment){
                            CommentDTO comment = new CommentDTO();
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

                                accountExtraRepository.findByAccount(account.getId()).ifPresent(accountExtra -> {
                                String profileImg = accountExtra.getProfileImg();
                                System.out.println("Commnted profile is : " + profileImg);
                                if (profileImg != null) {
                                    comment.setProfileImgUser(profileImg);
                                }
                            });
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
                        //check if the post is the owner
                        if(account.getId() == id){
                            feedDTO.set_Owner(true);
                        }

                        accountExtraRepository.findByAccount(account.getId()).ifPresent(accountExtra -> {
                            String profileImg = accountExtra.getProfileImg();
                            if (profileImg != null) {
                                feedDTO.setProfileImg(profileImg);
                            }
                        });
                        //check if the feed is save or not
                        // System.out.println(feedId + "Is the feed id");
                        // System.out.println((Integer) feed[4] + "is the accout id");
                        Optional<SaveModel> saveModel = saveRepository.findSaved(id, feedId);
                        if (saveModel.isPresent()) {
                            System.out.println("you saved this already " + feedId + " " + account.getId());
                            feedDTO.set_Save(true);
                        }

                        
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
                    List<CommentDTO> comments = new ArrayList<>();
                    List<Object[]> loadedComment = commentRepository.getAllComment(feedId);
                    for(Object[] commentRow : loadedComment){
                        CommentDTO comment = new CommentDTO();
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
                            accountExtraRepository.findByAccount(account.getId()).ifPresent(accountExtra -> {
                                String profileImg = accountExtra.getProfileImg();
                                System.out.println("Commnted profile is : " + profileImg);
                                if (profileImg != null) {
                                    comment.setProfileImgUser(profileImg);
                                }
                            });
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
                    //check if the post is the owner
                    if(account.getId() == id){
                        dto.set_Owner(true);
                    }
                    accountExtraRepository.findByAccount(account.getId()).ifPresent(accountExtra -> {
                        String profileImg = accountExtra.getProfileImg();
                        if (profileImg != null) {
                            dto.setProfileImg(profileImg);
                        }
                    });
                    // check if the feed is save or not
                    Optional<SaveModel> saveModel = saveRepository.findSaved(id,feedId);
                    if(saveModel.isPresent()){
                        System.out.println("you saved this already " + feedId + "" + account.getId());
                        dto.set_Save(true);
                    }

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
            e.printStackTrace();
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
