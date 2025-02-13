package com.example.schedulejpa.member.service;

import com.example.schedulejpa.comment.service.CommentWriter;
import com.example.schedulejpa.global.config.PasswordEncoder;
import com.example.schedulejpa.member.dto.MemberResponseDto;
import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.member.exception.InvalidPasswordException;
import com.example.schedulejpa.schedule.service.ScheduleWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final ScheduleWriter scheduleWriter;
    private final CommentWriter commentWriter;
    private final MemberReader memberReader;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<MemberResponseDto> getMembers() {

        List<Member> members = memberReader.findMembers();
        return members.stream().map(MemberResponseDto::from).toList();
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getMember(Long memberId) {
        Member member = memberReader.findById(memberId);

        return MemberResponseDto.from(member);
    }

    @Transactional
    public void updateMemberName(String email, String newName) {
        Member member = memberReader.findByEmail(email);
        member.updateName(newName);
    }

    @Transactional
    public void withdraw(String loginEmail, String password) {
        Member member = memberReader.findByEmail(loginEmail);

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new InvalidPasswordException();
        }

        scheduleWriter.softDeleteSchedulesByMember(member.getId());
        commentWriter.softDeleteCommentsByMember(member.getId());
        member.softDelete();
    }
}
