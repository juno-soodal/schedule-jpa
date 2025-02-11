package com.example.schedulejpa.member.service;

import com.example.schedulejpa.member.dto.MemberPatchRequestDto;
import com.example.schedulejpa.member.dto.MemberRequestDto;
import com.example.schedulejpa.member.dto.MemberResponseDto;
import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    @Transactional
    public MemberResponseDto createMember(MemberRequestDto requestDto) {
        if(memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.");
        }
        Member member = new Member(requestDto.getEmail(), requestDto.getName());
        memberRepository.save(member);
        return MemberResponseDto.from(member);
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> getMembers() {
        List<Member> members = memberRepository.findAll();

        return members.stream().map(MemberResponseDto::from).toList();
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"해당 유저가 없습니다."));
        return MemberResponseDto.from(member);
    }

    @Transactional
    public void updateMember(Long memberId, MemberRequestDto requestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"해당 유저가 없습니다."));
        if(memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.");
        }
        member.update(requestDto.getName(), requestDto.getEmail());

    }

    @Transactional
    public void updatePartialMember(Long memberId, MemberPatchRequestDto requestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"해당 유저가 없습니다."));

        if (StringUtils.hasText(requestDto.getEmail())) {
            member.updateEmail(requestDto.getEmail());
        }

        if (StringUtils.hasText(requestDto.getName())) {
            member.updateName(requestDto.getName());
        }

    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"해당 유저가 없습니다."));
        memberRepository.delete(member);
    }
}
