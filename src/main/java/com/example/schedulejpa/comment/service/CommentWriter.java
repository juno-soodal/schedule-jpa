package com.example.schedulejpa.comment.service;

import com.example.schedulejpa.comment.repository.CommentRepository;
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
}
