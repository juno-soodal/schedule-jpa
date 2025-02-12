package com.example.schedulejpa.auth.dto;

import lombok.Getter;

@Getter
public class LoginMember {

    private String email;
    private String name;

    private LoginMember(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static LoginMember of(String email, String name) {
        return new LoginMember(email, name);
    }

}
