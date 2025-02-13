package com.example.schedulejpa.auth.exception;

import com.example.schedulejpa.common.exception.ErrorCode;
import com.example.schedulejpa.common.exception.ScheduleAppException;

public class LoginFailedException extends ScheduleAppException {
    public LoginFailedException() {
        super(ErrorCode.LOGIN_FAILED);
    }
}
