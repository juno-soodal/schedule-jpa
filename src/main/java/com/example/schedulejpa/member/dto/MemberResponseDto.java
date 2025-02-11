package com.example.schedulejpa.member.dto;

import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.schedule.dto.ScheduleResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberResponseDto {

    private Long id;
    private String email;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private MemberResponseDto(Long id, String email, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public static MemberResponseDto from(Member member) {
        return new MemberResponseDto(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getUpdatedAt(),
                member.getUpdatedAt()
        );
    }
}
