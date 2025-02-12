package com.example.schedulejpa.comment.controller;

import com.example.schedulejpa.comment.dto.CommentResponseDto;
import com.example.schedulejpa.comment.service.CommentService;
import com.example.schedulejpa.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
        System.out.println(1);
        return new ResponseEntity<>(Response.of(commentService.getComments(scheduleId)), HttpStatus.OK);
    }
}
