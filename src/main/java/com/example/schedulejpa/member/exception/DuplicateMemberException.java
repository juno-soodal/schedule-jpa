package com.example.schedulejpa.member.exception;

import com.example.schedulejpa.global.common.exception.ErrorCode;
import com.example.schedulejpa.global.common.exception.ScheduleAppException;

public class DuplicateMemberException extends ScheduleAppException {
    public DuplicateMemberException() {
        super(ErrorCode.DUPLICATE_MEMBER);
    }
}
