package com.example.friendo.SaveFeature.DTO;

import java.time.LocalDateTime;
import java.util.List;

import com.example.friendo.FeedFeature.DTO.FeedDTO;
import com.example.friendo.FeedFeature.Model.Feed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveDTO {
    public LocalDateTime save_At;
    public List<FeedDTO> feed;
}
