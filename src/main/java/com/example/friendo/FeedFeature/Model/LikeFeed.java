package com.example.friendo.FeedFeature.Model;

import com.example.friendo.AccountFeature.Model.Account;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LikeFeed")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class LikeFeed {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "accountId")
    public Account account;
    
    @ManyToOne
    @JoinColumn(name = "feedId")
    public Feed feed;
}
