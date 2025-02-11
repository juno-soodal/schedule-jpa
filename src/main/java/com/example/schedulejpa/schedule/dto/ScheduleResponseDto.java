package com.example.schedulejpa.schedule.dto;

import com.example.schedulejpa.schedule.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private Long id;
    private String authorName;
    private String title;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updatedAt;

    public ScheduleResponseDto(Long id, String authorName, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static  ScheduleResponseDto of(Long id, String authorName, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new ScheduleResponseDto(
                id,
                authorName,
                title,
                content,
                createdAt,
                updatedAt
        );
    }

    public static ScheduleResponseDto fromSchedule(Schedule schedule) {
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getAuthorName(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }
}
