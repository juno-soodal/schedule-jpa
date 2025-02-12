package com.example.schedulejpa.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.domain.Page;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface Response<T> {

    T getData();

    static <T> Response<T> of(T data) {
        return new DefaultResponse<>(data);
    }

    static <T> Response<T> empty() {
        return new DefaultResponse<>(null);
    }

    static <T> PageResponse<T> fromPage(Page<T> pagedData) {
        return new PageResponse<>(
                pagedData.getContent(),
                pagedData.getPageable().getPageNumber(),
                pagedData.getPageable().getPageSize(),
                pagedData.getTotalPages(),
                pagedData.getTotalElements()
        );
    }
}
