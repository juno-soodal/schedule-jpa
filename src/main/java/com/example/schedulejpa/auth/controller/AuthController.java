package com.example.schedulejpa.auth.controller;

import com.example.schedulejpa.auth.dto.LoginRequestDto;
import com.example.schedulejpa.auth.service.AuthService;
import com.example.schedulejpa.global.constant.SessionConst;
import com.example.schedulejpa.global.response.Response;
import com.example.schedulejpa.auth.dto.SinupRequestDto;
import com.example.schedulejpa.member.dto.MemberResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Response<Void>> signup(@RequestBody @Valid SinupRequestDto requestDto) {
        authService.signup(requestDto);
        return new ResponseEntity<>(Response.empty(), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    public ResponseEntity<Response<Void>> login(@RequestBody @Valid LoginRequestDto requestDto, HttpServletRequest request) {

        MemberResponseDto loginMember = authService.login(requestDto.getEmail(), requestDto.getPassword());

        HttpSession session = request.getSession();

        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return new ResponseEntity<>(Response.empty(), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/logout")
    public ResponseEntity<Response<Void>> logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return new ResponseEntity<>(Response.empty(), HttpStatus.NO_CONTENT);
    }
}
