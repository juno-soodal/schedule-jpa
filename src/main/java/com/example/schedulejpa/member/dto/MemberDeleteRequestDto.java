package com.example.schedulejpa.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberDeleteRequestDto {

    @NotBlank
    private String password;
}
