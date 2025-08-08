package com.example.friendo.FeedFeature.Utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.friendo.AccountExtraFeature.Repository.AccountExtraRepository;
import com.example.friendo.AccountFeature.DTO.AccountDTO;
import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Repository.AccountRepository;
import com.example.friendo.FeedFeature.DTO.CommentDTO;
import com.example.friendo.FeedFeature.DTO.FeedDTO;
import com.example.friendo.FeedFeature.DTO.LikeDTO;
import com.example.friendo.FeedFeature.Repository.CommentRepository;
import com.example.friendo.FeedFeature.Repository.FeedRepository;
import com.example.friendo.FeedFeature.Repository.LikeRepository;
import com.example.friendo.FriendFeature.Service.FriendService;
import com.example.friendo.MicrosoftAzure.ImageMetaDataRepository;
import com.example.friendo.MicrosoftAzure.ImageMetaModel;
import com.example.friendo.MicrosoftAzure.imageMetaDataService;
import com.example.friendo.SaveFeature.Repository.SaveRepository;

@Service
public class FeedUtils {
    private FeedRepository feedRepository;
    private AccountRepository accountRepository;
    private FriendService friendService;
    private LikeRepository likeRepository;
    private imageMetaDataService imageMetaDataServices;
    private ImageMetaDataRepository imageMetaDataRepository;
    private CommentRepository commentRepository;
    private AccountExtraRepository accountExtraRepository;
    private SaveRepository saveRepository;
    @Autowired
    public FeedUtils(FeedRepository feedRepository,
            AccountRepository accountRepository,
            FriendService friendService,
            LikeRepository likeRepository,
            imageMetaDataService imageMetaDataServices,
            ImageMetaDataRepository imageMetaDataRepository,
            CommentRepository commentRepository,
            AccountExtraRepository accountExtraRepository,
            SaveRepository saveRepository)
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

}
