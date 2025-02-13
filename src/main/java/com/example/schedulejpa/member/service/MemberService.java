package com.example.schedulejpa.member.service;

import com.example.schedulejpa.comment.service.CommentService;
import com.example.schedulejpa.global.config.PasswordEncoder;
import com.example.schedulejpa.member.dto.MemberResponseDto;
import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.member.exception.InvalidPasswordException;
import com.example.schedulejpa.member.exception.MemberNotFoundException;
import com.example.schedulejpa.member.repository.MemberRepository;
import com.example.schedulejpa.schedule.service.ScheduleService;
import com.example.schedulejpa.schedule.service.ScheduleWriter;
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
    //    private final ScheduleService scheduleService;
    private final ScheduleWriter scheduleWriter;
    private final CommentService commentService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<MemberResponseDto> getMembers() {
        List<Member> members = memberRepository.findAll();

        return members.stream().map(MemberResponseDto::from).toList();
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());
        return MemberResponseDto.from(member);
    }

    @Transactional
    public void updateMemberName(String email, String newName) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new MemberNotFoundException());
        member.updateName(newName);

    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());
        memberRepository.delete(member);
    }

    @Transactional
    public void withdraw(String loginEmail, String password) {
        Member member = memberRepository.findByEmail(loginEmail).orElseThrow(() -> new MemberNotFoundException());

        //TODO 비밀번호 검증로직
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new InvalidPasswordException();
        }

        //TODO 연관엔티티 삭제로직
        scheduleWriter.softDeleteSchedulesByMember(member.getId());
        commentService.softDeleteCommentsByMember(member.getId());
        member.softDelete();
    }
}
