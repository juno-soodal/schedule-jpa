package com.example.schedulejpa.auth.service;

import com.example.schedulejpa.auth.dto.LoginMember;
import com.example.schedulejpa.auth.dto.SinupRequestDto;
import com.example.schedulejpa.auth.exception.LoginFailedException;
import com.example.schedulejpa.global.config.PasswordEncoder;
import com.example.schedulejpa.auth.exception.DuplicateMemberException;
import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.member.service.component.MemberFinder;
import com.example.schedulejpa.member.service.component.MemberReader;
import com.example.schedulejpa.member.service.component.MemberWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberReader memberReader;
    private final MemberWriter memberWriter;
    private final MemberFinder memberFinder;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SinupRequestDto requestDto) {

        //TODO 회원 복구 기능
        if( memberReader.exists(requestDto.getEmail())) {
            throw new DuplicateMemberException();
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        memberWriter.create(requestDto.getEmail(), requestDto.getName(), encodedPassword);


    }

    @Transactional(readOnly = true)
    public LoginMember login(String email, String password) {
        Member member = memberFinder.findByEmail(email);

        //TODO 비밀번호 검증로직
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new LoginFailedException();
        }

        return LoginMember.of(member.getEmail(),member.getName());
    }
}
