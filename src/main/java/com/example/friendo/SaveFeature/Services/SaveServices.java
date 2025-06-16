package com.example.friendo.SaveFeature.Services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.friendo.AccountExtraFeature.Repository.AccountExtraRepository;
import com.example.friendo.AccountFeature.DTO.AccountDTO;
import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Repository.AccountRepository;
import com.example.friendo.FeedFeature.DTO.CommentDTO;
import com.example.friendo.FeedFeature.DTO.FeedDTO;
import com.example.friendo.FeedFeature.DTO.LikeDTO;
import com.example.friendo.FeedFeature.Model.Feed;
import com.example.friendo.FeedFeature.Model.LikeFeed;
import com.example.friendo.FeedFeature.Repository.CommentRepository;
import com.example.friendo.FeedFeature.Repository.FeedRepository;
import com.example.friendo.FeedFeature.Repository.LikeRepository;
import com.example.friendo.MicrosoftAzure.ImageMetaDataRepository;
import com.example.friendo.MicrosoftAzure.ImageMetaModel;
import com.example.friendo.MicrosoftAzure.imageMetaDataService;
import com.example.friendo.SaveFeature.DTO.SaveDTO;
import com.example.friendo.SaveFeature.Model.SaveModel;
import com.example.friendo.SaveFeature.Repository.SaveRepository;

import jakarta.transaction.Transactional;

@Service
public class SaveServices {
    private SaveRepository saveRepository;
    private FeedRepository feedRepository;
    private AccountRepository accountRepository;
    private LikeRepository likeRepository;
    private imageMetaDataService imageMetaDataServices;
    private ImageMetaDataRepository imageMetaDataRepository;
    private CommentRepository commentRepository;
    private AccountExtraRepository accountExtraRepository;

    @Autowired
    public SaveServices(SaveRepository saveRepository,
            FeedRepository feedRepository,
            AccountRepository accountRepository,
            LikeRepository likeRepository,
            imageMetaDataService imageMetaDataServices,
            ImageMetaDataRepository imageMetaDataRepository,
            CommentRepository commentRepository,
            AccountExtraRepository accountExtraRepository){
        this.feedRepository = feedRepository;
        this.accountRepository = accountRepository;
        this.likeRepository = likeRepository;
        this.imageMetaDataServices = imageMetaDataServices;
        this.imageMetaDataRepository = imageMetaDataRepository;
        this.commentRepository = commentRepository;
        this.accountExtraRepository = accountExtraRepository;
        this.saveRepository = saveRepository;
    }
    @Transactional
    public Optional<SaveModel> createSave(Integer feedId,Integer userId){
        if(feedId == null){
            return Optional.empty();
        }
        if(userId == null){
            return Optional.empty();
        }
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new NoSuchElementException("No feed found with feed id : " + feedId));
        Account account = accountRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("No account found with the id : " + userId));

        SaveModel saveModel = new SaveModel();
        saveModel.setSave_AT(LocalDateTime.now());
        saveModel.setAccount(account);
        saveModel.setFeed(feed);
        return Optional.of(saveRepository.save(saveModel));
    }
    public void unsave(Integer feedId,Integer userId){
        if(feedId == null){
            throw new NoSuchElementException("Feed id is null");
        }
        if(userId == null){
            throw new NoSuchElementException("Account id is null");
        }
        System.out.println(feedId + " " + userId);
        Feed feed = feedRepository.findById(feedId).orElseThrow(() -> new NoSuchElementException("No feed found with feed id : " + feedId));
        Account account = accountRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("No account found with the id : " + userId));
        
        SaveModel saveModel = saveRepository.findSaved(userId,feedId).orElseThrow(() -> new NoSuchElementException("No Save found with the user id : " + userId + " and feed id of" + feedId));
        saveRepository.delete(saveModel);
    }
    public List<SaveDTO> getAllSaved(Integer userid) {
        List<SaveDTO> saveModels = new ArrayList<>();
        List<Object[]> data = saveRepository.findAllSaved(userid);

        for (Object[] row : data) {
            SaveDTO saveDTO = new SaveDTO();
            Integer feedId = (Integer) row[3];
            Feed feed = feedRepository.findById(feedId).orElse(null);

            if (feed != null) {
                FeedDTO feedDTO = new FeedDTO();
                feedDTO.setId(feed.getId());
                feedDTO.setContext(feed.getContext());
                feedDTO.setCreatedAt(String.valueOf(feed.getCreated_at()));
                feedDTO.setVisibility(String.valueOf(feed.getVisibility()));

                // ==== Comments ====
                List<CommentDTO> comments = new ArrayList<>();
                List<Object[]> loadedComment = commentRepository.getAllComment(feedId);
                for (Object[] commentRow : loadedComment) {
                    CommentDTO comment = new CommentDTO();
                    comment.setId((Integer) commentRow[0]);
                    comment.setContent((String) commentRow[1]);
                    comment.setCreated_At((String) commentRow[2]);

                    Integer commenterId = (Integer) commentRow[3];
                    accountRepository.findById(commenterId).ifPresent(creator -> {
                        Account account = new Account();
                        account.setFirstname(creator.getFirstname());
                        account.setLastname(creator.getLastname());
                        account.setEmail(creator.getEmail());
                        account.setId(creator.getId());
                        account.setUsername(creator.getUsername());
                        comment.setAccount(account);

                        accountExtraRepository.findByAccount(account.getId()).ifPresent(extra -> {
                            if (extra.getProfileImg() != null) {
                                comment.setProfileImgUser(extra.getProfileImg());
                            }
                        });
                    });

                    comments.add(comment);
                }
                feedDTO.setComments(comments);

                // ==== Feed Creator ====
                Integer creatorId = feed.getAccount().getId();
                AccountDTO passAccount = new AccountDTO();
                accountRepository.findById(creatorId).ifPresent(creator -> {
                    passAccount.setEmail(creator.getEmail());
                    passAccount.setFirstname(creator.getFirstname());
                    passAccount.setLastname(creator.getLastname());
                    passAccount.setId(creator.getId());
                    passAccount.setUsername(creator.getUsername());

                    accountExtraRepository.findByAccount(creator.getId()).ifPresent(extra -> {
                        if (extra.getProfileImg() != null) {
                            feedDTO.setProfileImg(extra.getProfileImg());
                        }
                    });
                });
                feedDTO.setAccount(passAccount);

                // ==== Images ====
                List<ImageMetaModel> imageList = new ArrayList<>();
                List<Object[]> loadedImage = imageMetaDataRepository.findByFeedId(feedId);
                for (Object[] imgRow : loadedImage) {
                    ImageMetaModel image = new ImageMetaModel();
                    image.setId((Integer) imgRow[0]);
                    image.setImageUrl((String) imgRow[2]);
                    imageList.add(image);
                }
                feedDTO.setImageMetaModels(imageList);

                // ==== Likes ====
                Optional<LikeFeed> likes = likeRepository.findLiker(feedId, userid);
                feedDTO.setLike(likes.isPresent());

                List<Object[]> allWhoLike = likeRepository.getAllWhoLike(feedId);
                List<LikeDTO> likeFeeds = new ArrayList<>();
                for (Object[] rowx : allWhoLike) {
                    Integer likerId = (Integer) rowx[1];
                    accountRepository.findById(likerId).ifPresent(userLiker -> {
                        AccountDTO userLikerDto = new AccountDTO();
                        userLikerDto.setEmail(userLiker.getEmail());
                        userLikerDto.setFirstname(userLiker.getFirstname());
                        userLikerDto.setLastname(userLiker.getLastname());
                        userLikerDto.setId(userLiker.getId());

                        LikeDTO likeDTO = new LikeDTO();
                        likeDTO.setAccount(userLikerDto);
                        likeFeeds.add(likeDTO);
                    });
                }
                feedDTO.setLikeFeed(likeFeeds);

                // ==== Saved? ====
                Optional<SaveModel> saveModel = saveRepository.findSaved(userid, feedId);
                if (saveModel.isPresent()) {
                    System.out.println(saveModel.get() + "+++<here " + feedId + " " + userid);
                    feedDTO.set_Save(true);
                } else {
                    System.out.println("No save found for user: " + userid + " and feed: " + feedId);
                }


                // ==== Final Add ====
                saveDTO.setFeed(List.of(feedDTO));
                saveDTO.setSave_At(((Timestamp) row[1]).toLocalDateTime());
                saveModels.add(saveDTO);
            }
        }

        return saveModels;
    }


}
