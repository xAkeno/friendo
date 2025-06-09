package com.example.friendo.FeedFeature.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.example.friendo.AccountFeature.DTO.AccountDTO;
import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.FeedFeature.Model.Comment;
import com.example.friendo.FeedFeature.Model.LikeFeed;
import com.example.friendo.MicrosoftAzure.ImageMetaModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class FeedDTO {
    private Integer id;
    private String context;
    private String createdAt;
    private String visibility;
    private AccountDTO account;
    private boolean like;
    private String profileImg;
    private List<ImageMetaModel> imageMetaModels;
    private List<Comment> comments;
    private List<LikeDTO> likeFeed;
}