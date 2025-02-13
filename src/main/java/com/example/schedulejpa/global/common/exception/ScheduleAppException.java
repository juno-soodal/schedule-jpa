package com.example.schedulejpa.global.common.exception;

import org.springframework.http.HttpStatus;

public class ScheduleAppException extends RuntimeException {

    private HttpStatus status;
    private String message;
    private String code;

    public ScheduleAppException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.status = errorCode.getStatus();
        this.message = errorCode.getDefaultMessage();
        this.code = errorCode.getCode();
    }
}
