package com.example.schedulejpa.member.service.component;

import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriter {

    private final MemberRepository memberRepository;

    public void create(String email, String name, String encodedPassword) {

        Member member = new Member(email, name, encodedPassword);
        memberRepository.save(member);
    }
}
