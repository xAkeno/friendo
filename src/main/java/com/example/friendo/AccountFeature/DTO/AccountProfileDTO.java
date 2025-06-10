package com.example.friendo.AccountFeature.DTO;

import java.util.List;

import com.example.friendo.AccountExtraFeature.Model.Status;
import com.example.friendo.FeedFeature.DTO.CommentDTO;
import com.example.friendo.FeedFeature.DTO.FeedDTO;
import com.example.friendo.FeedFeature.DTO.LikeDTO;
import com.example.friendo.FeedFeature.Model.Feed;
import com.example.friendo.MicrosoftAzure.ImageMetaModel;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AccountProfileDTO {
    private Integer id;
    private String firstname;
    private String lastName;
    private String username;
    private String gender;
    private String bio;
    private String country;
    private String city;
    private String school;
    private Status status;
    private String profileImg;
    private List<FeedDTO> feed;
    // private List<ImageMetaModel> imageMetaModels;
    // private List<CommentDTO> comments;
    // private List<LikeDTO> likeFeed;
}
