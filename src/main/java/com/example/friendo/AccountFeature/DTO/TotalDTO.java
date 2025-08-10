package com.example.friendo.AccountFeature.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalDTO {
    public int chatTotal;
    public int likeTotal;
    public int feedTotal;
    public int userTotal;
}
