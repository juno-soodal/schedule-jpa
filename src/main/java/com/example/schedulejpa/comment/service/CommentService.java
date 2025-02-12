package com.example.schedulejpa.comment.service;

import com.example.schedulejpa.comment.dto.CommentResponseDto;
import com.example.schedulejpa.comment.entity.Comment;
import com.example.schedulejpa.comment.repository.CommentRepository;
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

}
