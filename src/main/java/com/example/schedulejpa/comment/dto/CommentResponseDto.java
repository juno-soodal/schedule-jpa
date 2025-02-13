package com.example.schedulejpa.comment.dto;

import com.example.schedulejpa.comment.entity.Comment;
import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.schedule.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;
    private String commentContent;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updatedAt;
    private String authorName;

    private CommentResponseDto(Long id, String commentContent, LocalDateTime createdAt, LocalDateTime updatedAt, String authorName) {
        this.id = id;
        this.commentContent = commentContent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.authorName = authorName;
    }

    public static CommentResponseDto from(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getCommentContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getMember().getName()
        );
    }
}
