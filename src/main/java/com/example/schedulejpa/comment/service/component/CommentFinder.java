package com.example.schedulejpa.comment.service.component;

import com.example.schedulejpa.comment.entity.Comment;
import com.example.schedulejpa.comment.exception.CommentNotFoundException;
import com.example.schedulejpa.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentFinder {
    private final CommentRepository commentRepository;
    public List<Comment> findComments(Long scheduleId) {
        return commentRepository.findComments(scheduleId);
    }

    public Comment find(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException());
    }
}
