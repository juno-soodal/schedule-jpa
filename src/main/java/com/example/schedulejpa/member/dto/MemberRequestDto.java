package com.example.schedulejpa.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberRequestDto {

    @NotBlank
    private String name;
}
