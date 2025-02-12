package com.example.schedulejpa.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

}
