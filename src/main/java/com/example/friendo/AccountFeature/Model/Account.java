package com.example.friendo.AccountFeature.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.friendo.AccountExtraFeature.Model.AccountExtraModel;
import com.example.friendo.FeedFeature.Model.Comment;
import com.example.friendo.FeedFeature.Model.Feed;
import com.example.friendo.FeedFeature.Model.LikeFeed;
import com.example.friendo.FriendFeature.Model.Friend;
import com.example.friendo.SaveFeature.Model.SaveModel;
import com.example.friendo.Websocket.Model.ChatMessagee;
import com.example.friendo.Websocket.Model.ChatRoom;
import com.example.friendo.Websocket.Model.ChatRoomMembers;
import com.example.friendo.Websocket.Model.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="account")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Account implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false,name = "firstname")
    private String firstname;

    @Column(nullable = false,name = "lastname")
    private String lastname;

    @Column(nullable = false,name = "age")
    private int age;

    @Column(nullable = false,name = "gender")
    private String gender;

    @Column(nullable = false,name = "username")
    private String username;
    @Column(nullable = false,name="email")
    private String email;

    @Column(nullable = false,name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role")
    private Role role;

    @Column(name = "verification_code")
    private String Verification;

    @Column(name = "verification_expiration")
    private LocalDateTime verificationCodeExpiresAt;
    private boolean enabled;
    
    private Status status;

    @ManyToMany(
        mappedBy = "account",
        fetch = FetchType.LAZY
    )
    public List<Friend> friend = new ArrayList<>();

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    public List<Feed> feed = new ArrayList<>();

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    public List<Comment> comment = new ArrayList<>();

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    public List<LikeFeed> likeFeed = new ArrayList<>();

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    public List<SaveModel> save = new ArrayList<>();

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    public List<AccountExtraModel> accountExtraModels = new ArrayList<>();

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    public List<ChatRoomMembers> chatRoomMembers = new ArrayList<>();

    // @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    // public List<ChatRoom> chatRoom = new ArrayList<>();

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    public List<ChatMessagee> chatMessagee = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
