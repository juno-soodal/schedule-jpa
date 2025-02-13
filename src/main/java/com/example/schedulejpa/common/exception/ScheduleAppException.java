package com.example.schedulejpa.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
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
