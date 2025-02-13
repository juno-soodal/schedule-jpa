package com.example.schedulejpa.comment.controller;

import com.example.schedulejpa.auth.dto.LoginMember;
import com.example.schedulejpa.comment.dto.CommentRequestDto;
import com.example.schedulejpa.comment.dto.CommentResponseDto;
import com.example.schedulejpa.comment.service.CommentService;
import com.example.schedulejpa.common.resolver.Login;
import com.example.schedulejpa.common.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<Response<List<CommentResponseDto>>> getComments(@RequestParam Long scheduleId) {
        return new ResponseEntity<>(Response.of(commentService.getComments(scheduleId)), HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Response<CommentResponseDto>> getComment(@PathVariable Long commentId) {
        return new ResponseEntity<>(Response.of(commentService.getComment(commentId)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response<Void>> createComment(@RequestBody @Valid CommentRequestDto requestDto, @Login LoginMember loginMember, @RequestParam Long scheduleId) {
        commentService.createComment( scheduleId,loginMember.getEmail(),requestDto);
        return new ResponseEntity<>(Response.empty(), HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Response<Void>> updateComment(@PathVariable Long commentId, @Login LoginMember loginMember, @RequestBody @Valid CommentRequestDto requestDto) {
        commentService.updateComment(commentId,loginMember.getEmail(), requestDto);
        return new ResponseEntity<>(Response.empty(), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Response<Void>> deleteComment(@PathVariable Long commentId, @Login LoginMember loginMember) {
        commentService.deleteByMemberEmail(commentId,loginMember.getEmail());
        return new ResponseEntity<>(Response.empty(), HttpStatus.NO_CONTENT);
    }
}
