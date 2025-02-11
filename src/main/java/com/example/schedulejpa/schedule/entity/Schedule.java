package com.example.schedulejpa.schedule.entity;

import com.example.schedulejpa.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "schedule")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authorName;

    private String title;

    @Lob
    private String content;

    public Schedule() {
    }

    public Schedule(String authorName, String title, String content) {
        this.authorName = authorName;
        this.title = title;
        this.content = content;
    }

    public void update(String authorName, String title, String content) {
        this.authorName = authorName;
        this.title = title;
        this.content = content;
    }

    public void updateAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
