package com.example.schedulejpa.comment.service;

import com.example.schedulejpa.comment.dto.CommentRequestDto;
import com.example.schedulejpa.comment.dto.CommentResponseDto;
import com.example.schedulejpa.comment.entity.Comment;
import com.example.schedulejpa.comment.repository.CommentRepository;
import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.member.repository.MemberRepository;
import com.example.schedulejpa.schedule.entity.Schedule;
import com.example.schedulejpa.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    //TODO service로 변경
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments(Long scheduleId) {
        if(!scheduleRepository.existsById(scheduleId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "없는 일정입니다.");
        }

        List<Comment> comments = commentRepository.findComments(scheduleId);
        return comments.stream().map(comment -> CommentResponseDto.from(comment)).toList();
    }

    @Transactional(readOnly = true)
    public CommentResponseDto getComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "없는 댓글입니다."));

        return CommentResponseDto.from(comment);
    }

    @Transactional
    public void createComment(Long scheduleId, String loginEmail, CommentRequestDto requestDto) {
        Member member = memberRepository.findByEmail(loginEmail).orElseThrow(() -> new IllegalArgumentException("없는 유저입니다."));

        Schedule schedule = scheduleRepository.findSchedule(scheduleId).orElseThrow(() -> new IllegalArgumentException("없는 일정입니다."));

        Comment comment = new Comment(requestDto.getCommentContent(), member,schedule);
        commentRepository.save(comment);

    }

    @Transactional
    public void updateComment(Long commentId, String loginEmail, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "없는 댓글입니다."));
        //TODO 리팩토링 필요
        if (!comment.getMember().getEmail().equals(loginEmail)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "수정권한이 없습니다.");
        }

        comment.updateCommentContent(requestDto.getCommentContent());
    }

    @Transactional
    public void deleteByMemberEmail(Long commentId, String loginEmail) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "없는 댓글입니다."));

        //TODO 리팩토링 필요
        if (!comment.getMember().getEmail().equals(loginEmail)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "수정권한이 없습니다.");
        }
        commentRepository.deleteComment(comment);
    }

    public void softDeleteCommentsByMember(Long memberId) {

        commentRepository.bulkUpdateDeletedAtByMember(memberId);
    }

    public void deleteAllByScheduleId(Long scheduleId) {
        commentRepository.bulkDelete(scheduleId);
    }
}
