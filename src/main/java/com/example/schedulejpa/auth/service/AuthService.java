package com.example.schedulejpa.auth.service;

import com.example.schedulejpa.auth.dto.LoginMember;
import com.example.schedulejpa.auth.dto.SinupRequestDto;
import com.example.schedulejpa.member.dto.MemberResponseDto;
import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.member.repository.MemberRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    @Transactional
    public void signup(SinupRequestDto requestDto) {
        //TODO 회원 복구 기능
        if(memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.");
        }
        Member member = new Member(requestDto.getEmail(), requestDto.getName(), requestDto.getPassword());
        memberRepository.save(member);

    }

    @Transactional(readOnly = true)
    public LoginMember login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 맞지 않습니다."));

        //TODO 비밀번호 검증로직
        if (!member.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 맞지 않습니다.");
        }

        return LoginMember.of(member.getEmail(),member.getName());
    }
}
