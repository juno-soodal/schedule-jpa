package com.example.schedulejpa.comment.entity;

import com.example.schedulejpa.global.entity.BaseEntity;
import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.schedule.entity.Schedule;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Getter
@Entity
@SQLRestriction("deleted_at is null")
@Table(name = "comment")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String commentContent;

    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected Comment() {
    }

    public Comment(String commentContent, Member member) {
        this.commentContent = commentContent;
        this.member = member;
    }

    public void updateCommentContent( String commentContent) {
        this.commentContent = commentContent;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
