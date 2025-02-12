package com.example.schedulejpa.member.controller;

import com.example.schedulejpa.auth.dto.LoginMember;
import com.example.schedulejpa.global.resolver.Login;
import com.example.schedulejpa.member.dto.MemberDeleteRequestDto;
import com.example.schedulejpa.member.dto.MemberRequestDto;
import com.example.schedulejpa.member.dto.MemberResponseDto;
import com.example.schedulejpa.member.service.MemberService;
import com.example.schedulejpa.global.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<Response<List<MemberResponseDto>>> getMembers() {
        return new ResponseEntity<>(Response.of(memberService.getMembers()), HttpStatus.OK);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<Response<MemberResponseDto>> getMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(Response.of(memberService.getMember(memberId)));
    }

    @PatchMapping
    public ResponseEntity<Void> updateMemberName(@Login LoginMember loginMember, @RequestBody @Valid MemberRequestDto requestDto) {
        memberService.updateMemberName(loginMember.getEmail(), requestDto.getName());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> withdraw(@Login LoginMember loginMember, @RequestBody @Valid MemberDeleteRequestDto requestDto) {
        log.info("password = {}", requestDto.getPassword());
        memberService.withdraw(loginMember.getEmail(), requestDto.getPassword());

        return ResponseEntity.noContent().build();
    }
}
