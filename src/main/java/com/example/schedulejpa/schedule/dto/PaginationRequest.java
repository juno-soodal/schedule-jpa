package com.example.schedulejpa.schedule.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class PaginationRequest {

    @Min(value = 1, message = "페이지는 1번부터 시작입니다.")
    private int page;
    @Min(value = 10, message = "사이즈는 10부터 시작입니다.")
    private int size;
}
