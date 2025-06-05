package com.example.friendo.AccountFeature.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponses {
    private String token;
    private long expiration;

    public LoginResponses(String token,long expiration){
        this.token = token;
        this.expiration = expiration;
    }
}
