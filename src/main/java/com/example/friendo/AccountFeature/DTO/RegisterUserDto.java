package com.example.friendo.AccountFeature.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    private String firstname;
    private String lastname;
    private int age;
    private String gender;
    private String email;
    private String password;
    public String username;
}
