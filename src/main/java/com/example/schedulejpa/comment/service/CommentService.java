package com.example.schedulejpa.comment.service;

import com.example.schedulejpa.comment.dto.CommentRequestDto;
import com.example.schedulejpa.comment.dto.CommentResponseDto;
import com.example.schedulejpa.comment.entity.Comment;
import com.example.schedulejpa.comment.service.component.CommentFinder;
import com.example.schedulejpa.comment.service.component.CommentWriter;
import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.member.exception.UnAuthorizedAccessException;
import com.example.schedulejpa.member.service.component.MemberFinder;
import com.example.schedulejpa.schedule.entity.Schedule;
import com.example.schedulejpa.schedule.exception.ScheduleNotFoundException;
import com.example.schedulejpa.schedule.service.component.ScheduleFinder;
import com.example.schedulejpa.schedule.service.component.ScheduleReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentWriter commentWriter;
    private final CommentFinder commentFinder;
    private final ScheduleFinder scheduleFinder;
    private final ScheduleReader scheduleReader;
    private final MemberFinder memberFinder;
    //TODO service로 변경

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments(Long scheduleId) {

        if(!scheduleReader.existsById(scheduleId)){
            throw new ScheduleNotFoundException();
        }

        List<Comment> comments = commentFinder.findComments(scheduleId);

        return comments.stream().map(comment -> CommentResponseDto.from(comment)).toList();
    }

    @Transactional(readOnly = true)
    public CommentResponseDto getComment(Long commentId) {
        Comment comment = commentFinder.find(commentId);

        return CommentResponseDto.from(comment);
    }

    @Transactional
    public void createComment(Long scheduleId, String loginEmail, CommentRequestDto requestDto) {
        Member member = memberFinder.findByEmail(loginEmail);

        Schedule schedule = scheduleFinder.findSchedule(scheduleId);

        commentWriter.create(member, schedule, requestDto);


    }

    @Transactional
    public void updateComment(Long commentId, String loginEmail, CommentRequestDto requestDto) {
        Comment comment = commentFinder.find(commentId);

        if (!comment.getMember().getEmail().equals(loginEmail)) {
            throw new UnAuthorizedAccessException();
        }

        comment.update(requestDto.getCommentContent());
    }

    @Transactional
    public void deleteByMemberEmail(Long commentId, String loginEmail) {
        Comment comment = commentFinder.find(commentId);

        //TODO 리팩토링 필요
        if (!comment.getMember().isSameEmail(loginEmail)) {
            throw new UnAuthorizedAccessException();
        }

        commentWriter.delete(comment);

    }
}
