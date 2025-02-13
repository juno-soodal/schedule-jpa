package com.example.schedulejpa.global.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER_NOT_FOUND","회원을 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"INVALID_PASSWORD" , "비밀번호가 일치하지 않습니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.BAD_REQUEST,"SCHEDULE_NOT_FOUND","일정을 찾을 수 없습니다." ),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED,"UNAUTHORIZED_ACCESS","해당 작업을 수행할 권한이 없습니다"),
    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST,"COMMENT_NOT_FOUND", "일정을 찾을 수 없습니다."),
    DUPLICATE_MEMBER(HttpStatus.BAD_REQUEST,"DUPLICATE_MEMBER","이미 존재하는 회원입니다." ),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED,"LOGIN_FAILED","아이디 또는 비밀번호가 맞지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String defaultMessage;

    ErrorCode(HttpStatus status, String code, String defaultMessage) {
        this.status =status;
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
}
