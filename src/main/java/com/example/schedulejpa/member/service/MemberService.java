package com.example.schedulejpa.member.service;

import com.example.schedulejpa.member.dto.MemberResponseDto;
import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.member.repository.MemberRepository;
import com.example.schedulejpa.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ScheduleService scheduleService;

    @Transactional(readOnly = true)
    public List<MemberResponseDto> getMembers() {
        List<Member> members = memberRepository.findAll();

        return members.stream().map(MemberResponseDto::from).toList();
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 유저가 없습니다."));
        return MemberResponseDto.from(member);
    }

    @Transactional
    public void updateMemberName(String email, String newName) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 유저가 없습니다."));
        member.updateName(newName);

    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 유저가 없습니다."));
        memberRepository.delete(member);
    }

    @Transactional
    public void withdraw(String loginEmail, String password) {
        Member member = memberRepository.findByEmail(loginEmail).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 유저가 없습니다."));

        //TODO 비밀번호 검증로직
        if (!member.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지않습니다.");
        }
        //TODO 연관엔티티 삭제로직
        scheduleService.softDeleteSchedulesByMember(loginEmail);
        member.softDelete();
    }
}
