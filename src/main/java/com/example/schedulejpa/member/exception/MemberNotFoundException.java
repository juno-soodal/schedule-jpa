package com.example.schedulejpa.member.exception;

import com.example.schedulejpa.global.common.exception.ErrorCode;
import com.example.schedulejpa.global.common.exception.ScheduleAppException;

public class MemberNotFoundException extends ScheduleAppException {
    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
