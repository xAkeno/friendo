package com.example.friendo.Websocket.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.friendo.AccountFeature.Model.Account;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @Column(name = "name",nullable = true)
    public String name;

    @Column(name = "description",nullable = true)
    public String description;

    @Column(name = "created_at",nullable = false)
    public LocalDateTime created_at;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoomMembers> chatRoomMembers = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessagee> chatMessages = new ArrayList<>();;

    // @ManyToOne
    // @JoinColumn(name = "account_id")
    // private Account account;

}
