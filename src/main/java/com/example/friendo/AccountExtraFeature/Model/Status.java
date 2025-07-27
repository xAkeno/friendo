package com.example.friendo.AccountExtraFeature.Model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    SINGLE,MARRIED,DIVORCED,WIDOWED,IN_A_RELATIONSHIP,NONE;

    @JsonCreator
    public static Status fromTo(String value){
        return switch(value.toLowerCase()){
            case "single" -> SINGLE;
            case "married" -> MARRIED;
            case "divorced" -> DIVORCED;
            case "widowed" -> WIDOWED;
            case "in a relationship" -> IN_A_RELATIONSHIP;
            case "none" -> NONE;
            default -> throw new IllegalArgumentException("Invalid value of " + value);
        };
    };
};
