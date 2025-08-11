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
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "active",nullable = false)
    private boolean active;
    @Column(name = "volume",nullable = false)
    private Long volume;
    @Column(name = "data",nullable = false)
    private String data;
    @Column(name = "category",nullable = false)
    private String category;
    @Column(name = "newslink",nullable = false)
    private String Newslink;
}
