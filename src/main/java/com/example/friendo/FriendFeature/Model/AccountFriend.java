package com.example.friendo.FriendFeature.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account_friend")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AccountFriend.class) // Composite key class
public class AccountFriend {

    @Id
    @Column(name = "account_id")
    private Integer accountId;

    @Id
    @Column(name = "friend_id")
    private Integer friendId;
}