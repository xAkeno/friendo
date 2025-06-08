package com.example.friendo.FeedFeature.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.MicrosoftAzure.ImageMetaModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "Feed")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Feed {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;

        @Column(name = "context", nullable = false)
        private String context;
        @Column(name = "created_at", nullable = false)
        private LocalDateTime created_at;

        @Enumerated(EnumType.STRING)
        @Column(name = "visibility", nullable = false)
        private Visibility visibility;

        @ManyToOne
        @JoinColumn(name = "accountId")
        public Account account;

        @OneToMany(mappedBy = "feed",cascade = CascadeType.ALL)
        public List<Comment> comment = new ArrayList<>();

        @OneToMany(mappedBy = "feed",cascade = CascadeType.ALL)
        public List<LikeFeed> like = new ArrayList<>();

        @OneToMany(mappedBy = "feed",cascade = CascadeType.ALL)
        public List<ImageMetaModel> image = new ArrayList<>();
}
