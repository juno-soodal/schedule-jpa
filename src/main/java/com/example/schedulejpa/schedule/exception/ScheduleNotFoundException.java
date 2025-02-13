package com.example.schedulejpa.schedule.exception;

import com.example.schedulejpa.common.exception.ErrorCode;
import com.example.schedulejpa.common.exception.ScheduleAppException;

public class ScheduleNotFoundException extends ScheduleAppException {
    public ScheduleNotFoundException() {
        super(ErrorCode.SCHEDULE_NOT_FOUND);
    }
}
