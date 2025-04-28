package com.example.friendo.FeedFeature.DTO;

import java.util.List;

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
public class LikeDTO {
    private Integer id;
    private Feed feed;
    private List<Account> account;
}