package com.example.friendo.FeedFeature.Model;

import com.example.friendo.AccountFeature.Model.Account;

import jakarta.persistence.Column;
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
@Table(name = "Comment")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "content",nullable = false)
    private String content;

    @Column(name = "createdd_At",nullable = false)
    private String created_At;

    @ManyToOne
    @JoinColumn(name = "feedId")
    public Feed feed;

    @ManyToOne
    @JoinColumn(name = "accountId")
    public Account account;

}
