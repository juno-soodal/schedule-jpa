package com.example.schedulejpa.comment.exception;

import com.example.schedulejpa.common.exception.ErrorCode;
import com.example.schedulejpa.common.exception.ScheduleAppException;

public class CommentNotFoundException extends ScheduleAppException {
    public CommentNotFoundException() {
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}
