package com.example.schedulejpa.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DefaultResponse<T> implements Response<T> {

    private final T data;

    @Override
    public T getData() {
        return data;
    }
}
