package com.example.schedulejpa.common.exception;

import com.example.schedulejpa.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse handleValidationException(MethodArgumentNotValidException e) {
        String errorMessage = e.getFieldErrors().stream().
                findFirst().
                map(fieldError -> fieldError.getDefaultMessage()).
                orElseThrow(() -> new IllegalStateException("검증 에러가 반드시 존재해야 합니다."));
        return ErrorResponse.of("ARGUMENT_NOT_VALID", errorMessage);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleScheduleAppException(ScheduleAppException e) {
        return new ResponseEntity<>(ErrorResponse.of(e.getCode(), e.getMessage()), e.getStatus());
    }
}
