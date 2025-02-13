package com.example.schedulejpa.member.service.component;

import com.example.schedulejpa.member.repository.MemberRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReader {

    private final MemberRepository memberRepository;

    public boolean exists(String email) {
        return memberRepository.existsByEmail(email);
    }
}
