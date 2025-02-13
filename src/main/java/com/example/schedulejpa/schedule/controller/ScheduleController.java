package com.example.schedulejpa.schedule.controller;

import com.example.schedulejpa.auth.dto.LoginMember;
import com.example.schedulejpa.common.resolver.Login;
import com.example.schedulejpa.common.response.Response;
import com.example.schedulejpa.schedule.dto.PaginationRequest;
import com.example.schedulejpa.schedule.dto.SchedulePatchRequestDto;
import com.example.schedulejpa.schedule.dto.ScheduleRequestDto;
import com.example.schedulejpa.schedule.dto.ScheduleResponseDto;
import com.example.schedulejpa.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<Response<ScheduleResponseDto>> getSchedules(@ModelAttribute PaginationRequest paginationRequest) {
        return new ResponseEntity<>(Response.fromPage(scheduleService.getSchedules(paginationRequest.getPage(), paginationRequest.getSize())),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response<ScheduleResponseDto>> createSchedule(@Login LoginMember loginMember, @RequestBody @Valid ScheduleRequestDto requestDto) {
        return new ResponseEntity<>(Response.of(scheduleService.createSchedule(loginMember.getEmail(), requestDto)), HttpStatus.CREATED);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<Response<ScheduleResponseDto>> getSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(Response.of(scheduleService.getSchedule(scheduleId)));
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody @Valid ScheduleRequestDto requestDto,
            @Login LoginMember loginMember) {
            scheduleService.updateSchedule(scheduleId,loginMember.getEmail(),requestDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody SchedulePatchRequestDto requestDto,
            @Login LoginMember loginMember) {
            scheduleService.updatePartialSchedule(scheduleId,loginMember.getEmail(), requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId, @Login LoginMember loginMember) {
        scheduleService.deleteSchedule(scheduleId, loginMember.getEmail());

        return ResponseEntity.noContent().build();
    }
}
