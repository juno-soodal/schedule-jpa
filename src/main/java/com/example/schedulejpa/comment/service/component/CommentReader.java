package com.example.schedulejpa.comment.service.component;


import com.example.schedulejpa.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentReader {

    private final CommentRepository commentRepository;
}
