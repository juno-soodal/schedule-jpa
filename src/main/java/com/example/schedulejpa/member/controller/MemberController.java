package com.example.schedulejpa.member.controller;

import com.example.schedulejpa.member.dto.MemberPatchRequestDto;
import com.example.schedulejpa.member.dto.MemberResponseDto;
import com.example.schedulejpa.member.service.MemberService;
import com.example.schedulejpa.global.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PatchMapping("/{memberId}")
    public ResponseEntity<Void> updateMember(
            @PathVariable Long memberId, @RequestBody @Valid MemberPatchRequestDto requestDto) {
        memberService.updatePartialMember(memberId, requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);

        return ResponseEntity.noContent().build();
    }
}
