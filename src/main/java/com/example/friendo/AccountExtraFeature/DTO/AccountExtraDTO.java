package com.example.friendo.AccountExtraFeature.DTO;


import com.example.friendo.AccountExtraFeature.Model.Status;
import com.example.friendo.AccountFeature.Model.Account;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountExtraDTO {
    private Integer id;

    private String bio;

    private String country;

    private String city;

    private String school;

    private Status status;

    private String profileImg;

    private boolean is_Owner;

    private Account account;
}
