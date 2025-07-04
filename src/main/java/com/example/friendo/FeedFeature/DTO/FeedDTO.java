package com.example.friendo.FeedFeature.DTO;

import java.util.List;

import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.FeedFeature.Model.Comment;
import com.example.friendo.FeedFeature.Model.LikeFeed;

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
    private Integer account;
    // private Comment comments;
    // private LikeFeed likeFeed;
}