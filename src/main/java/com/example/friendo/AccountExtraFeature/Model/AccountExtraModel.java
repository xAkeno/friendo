package com.example.friendo.AccountExtraFeature.Model;

import org.springframework.web.multipart.MultipartFile;

import com.example.friendo.AccountFeature.Model.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Getter
@Setter
@Table(name = "AccountExtra")
@NoArgsConstructor
@AllArgsConstructor
public class AccountExtraModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "bio",nullable = true)
    private String bio;

    @Column(name = "country",nullable = true)
    private String country;

    @Column(name = "city",nullable = true)
    private String city;

    @Column(name = "school",nullable = true)
    private String school;

    @Column(name = "status",nullable = true)
    private Status status;

    @Column(name = "profileImg",nullable = true)
    private String profileImg;

    @ManyToOne
    @JoinColumn(name = "accountId")
    @JsonIgnore
    public Account account;
}
