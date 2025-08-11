package com.example.friendo.TrendingFeature.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trandingmodel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrendingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    @Column(name = "title",nullable = true)
    private String title;
    @Column(name = "active",nullable = true)
    private boolean active;
    @Column(name = "volume",nullable = true)
    private Long volume;
    @Column(name = "data",nullable = true)
    private String data;
    @Column(name = "category",nullable = true)
    private String category;
    @Column(name = "newslink",nullable = true)
    private String Newslink;
}
