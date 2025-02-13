package com.example.schedulejpa.member.exception;

import com.example.schedulejpa.global.common.exception.ErrorCode;
import com.example.schedulejpa.global.common.exception.ScheduleAppException;

public class UnAuthorizedAccessException extends ScheduleAppException {
    public UnAuthorizedAccessException() {
        super(ErrorCode.UNAUTHORIZED_ACCESS);
    }
}
