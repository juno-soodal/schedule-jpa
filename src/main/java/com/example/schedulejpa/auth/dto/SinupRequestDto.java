package com.example.schedulejpa.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SinupRequestDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

}
