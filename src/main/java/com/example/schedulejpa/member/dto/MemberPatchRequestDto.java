package com.example.schedulejpa.member.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class MemberPatchRequestDto {

    @Email
    private String email;

    private String name;
}
