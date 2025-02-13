package com.example.schedulejpa.auth.exception;

import com.example.schedulejpa.common.exception.ErrorCode;
import com.example.schedulejpa.common.exception.ScheduleAppException;

public class DuplicateMemberException extends ScheduleAppException {
    public DuplicateMemberException() {
        super(ErrorCode.DUPLICATE_MEMBER);
    }
}
