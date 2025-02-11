package com.example.schedulejpa.member.entity;

import com.example.schedulejpa.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    public Member() {
    }

    public Member(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public void update( String name, String email) {
        this.email = email;
        this.name = name;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
