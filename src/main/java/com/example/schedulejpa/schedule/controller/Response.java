package com.example.schedulejpa.schedule.controller;

import com.example.schedulejpa.schedule.dto.ScheduleResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface Response<T> {

    T getData();

    static <T> Response<T> of(T data) {
        return new DefaultResponse<>(data);
    }

    static <T> Response<T> empty() {
        return new DefaultResponse<>(null);
    }
}
