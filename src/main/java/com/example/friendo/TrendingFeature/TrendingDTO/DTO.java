package com.example.friendo.TrendingFeature.TrendingDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DTO {
    private String title;
    private boolean active;
    private Long volume;
    private String data;
    private String category;
}
