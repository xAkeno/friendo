package com.example.friendo.FeedFeature.DTO;

import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.FeedFeature.Model.Feed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private Integer id;
    private String content;
    private String created_At;
    private Feed feed;
    private Account account;
    private Integer userid;
    private String profileImgUser;
}