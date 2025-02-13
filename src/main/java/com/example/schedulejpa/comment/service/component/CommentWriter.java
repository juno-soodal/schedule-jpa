package com.example.schedulejpa.comment.service.component;

import com.example.schedulejpa.comment.dto.CommentRequestDto;
import com.example.schedulejpa.comment.entity.Comment;
import com.example.schedulejpa.comment.repository.CommentRepository;
import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.schedule.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentWriter {

    private final CommentRepository commentRepository;

    public void deleteAllByScheduleId(Long scheduleId) {
        commentRepository.bulkDeleteByScheduleId(scheduleId);
    }

    public void softDeleteCommentsByMember(Long memberId) {
        commentRepository.bulkUpdateDeletedAtByMember(memberId);
    }

    public void create(Member member, Schedule schedule, CommentRequestDto requestDto) {
        Comment comment = new Comment(requestDto.getCommentContent(), member);
        schedule.addComment(comment);
        commentRepository.save(comment);
    }

    public void delete(Comment comment) {
        commentRepository.deleteComment(comment);
    }
}
