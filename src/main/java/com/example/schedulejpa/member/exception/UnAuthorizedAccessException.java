package com.example.schedulejpa.member.exception;

import com.example.schedulejpa.common.exception.ErrorCode;
import com.example.schedulejpa.common.exception.ScheduleAppException;

public class UnAuthorizedAccessException extends ScheduleAppException {
    public UnAuthorizedAccessException() {
        super(ErrorCode.UNAUTHORIZED_ACCESS);
    }
}
