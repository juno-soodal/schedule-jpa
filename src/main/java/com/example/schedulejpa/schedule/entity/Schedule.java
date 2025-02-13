package com.example.schedulejpa.schedule.entity;

import com.example.schedulejpa.comment.entity.Comment;
import com.example.schedulejpa.global.entity.BaseEntity;
import com.example.schedulejpa.member.entity.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@SQLRestriction(value = "deleted_at is null")
@Table(name = "schedule")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    private String authorName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    @Lob
    private String content;

    @OneToMany(mappedBy = "schedule")
    private List<Comment> comments = new ArrayList<>();

    private LocalDateTime deletedAt;


    protected Schedule() {
    }

    public Schedule(Member member, String title, String content) {
        this.member = member;
        this.title = title;
        this.content = content;
    }

    public void addComment(Comment comment) {
        comment.setSchedule(this);
        this.comments.add(comment);

    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

}
