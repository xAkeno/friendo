package com.example.friendo.AccountFeature.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
    private String firstname;
    private String lastname;
    private Integer id;
    private String email;
}
