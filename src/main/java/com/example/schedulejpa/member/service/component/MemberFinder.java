package com.example.schedulejpa.member.service.component;

import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.member.exception.MemberNotFoundException;
import com.example.schedulejpa.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberFinder {

    private final MemberRepository memberRepository;

    public Member findByEmail(String loginEmail) {
        return memberRepository.findByEmail(loginEmail).orElseThrow(() -> new MemberNotFoundException());
    }

    public  List<Member> findMembers() {
        return memberRepository.findMembers();
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());
    }
}
