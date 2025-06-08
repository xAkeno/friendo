package com.example.friendo.AccountFeature.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyUserDto {
    public String email;
    public String verificationCode;
}
