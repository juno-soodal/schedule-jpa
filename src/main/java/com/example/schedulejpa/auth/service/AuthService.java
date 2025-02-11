package com.example.schedulejpa.auth.service;

import com.example.schedulejpa.auth.dto.SinupRequestDto;
import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public void signup(SinupRequestDto requestDto) {
        if(memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.");
        }
        Member member = new Member(requestDto.getEmail(), requestDto.getName(), requestDto.getPassword());
        memberRepository.save(member);

    }
}
