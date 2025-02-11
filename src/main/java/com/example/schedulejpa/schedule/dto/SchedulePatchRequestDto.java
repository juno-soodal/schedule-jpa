package com.example.schedulejpa.schedule.dto;

import lombok.Getter;

@Getter
public class SchedulePatchRequestDto {

    private String authorName;
    private String title;
    private String content;

}
