package com.example.schedulejpa.member.exception;

import com.example.schedulejpa.global.common.exception.ErrorCode;
import com.example.schedulejpa.global.common.exception.ScheduleAppException;

public class InvalidPasswordException extends ScheduleAppException {
    public InvalidPasswordException() {
        super(ErrorCode.INVALID_PASSWORD);
    }
}
