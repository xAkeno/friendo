package com.example.friendo.FriendFeature.Model;

import java.util.ArrayList;
import java.util.List;

import com.example.friendo.AccountFeature.Model.Account;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Friend")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_id",nullable = false)
    private int user_id;

    @Column(name = "status",nullable = false)
    private Status status;

    @Column(name = "createdAt",nullable = false)
    private String createdAt;

    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = {CascadeType.PERSIST,CascadeType.MERGE}
    )
    @JoinTable(
        name = "Account_Friend",
        joinColumns = {
            @JoinColumn(name ="friend_id",referencedColumnName = "id")
        },
        inverseJoinColumns ={
            @JoinColumn(name ="account_id",referencedColumnName = "id")
        }
    )
    public List<Account> account = new ArrayList<>();
}
