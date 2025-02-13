package com.example.schedulejpa.member.service;

import com.example.schedulejpa.comment.service.component.CommentWriter;
import com.example.schedulejpa.global.config.PasswordEncoder;
import com.example.schedulejpa.member.dto.MemberResponseDto;
import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.member.exception.InvalidPasswordException;
import com.example.schedulejpa.member.service.component.MemberFinder;
import com.example.schedulejpa.schedule.service.component.ScheduleWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final ScheduleWriter scheduleWriter;
    private final CommentWriter commentWriter;
    private final MemberFinder memberFinder;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<MemberResponseDto> getMembers() {

        List<Member> members = memberFinder.findMembers();
        return members.stream().map(MemberResponseDto::from).toList();
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getMember(Long memberId) {
        Member member = memberFinder.findById(memberId);

        return MemberResponseDto.from(member);
    }

    @Transactional
    public void updateMemberName(String email, String newName) {
        Member member = memberFinder.findByEmail(email);
        member.updateName(newName);
    }

    @Transactional
    public void withdraw(String loginEmail, String password) {
        Member member = memberFinder.findByEmail(loginEmail);

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new InvalidPasswordException();
        }

        scheduleWriter.softDeleteSchedulesByMember(member.getId());
        commentWriter.softDeleteCommentsByMember(member.getId());
        member.softDelete();
    }
}
