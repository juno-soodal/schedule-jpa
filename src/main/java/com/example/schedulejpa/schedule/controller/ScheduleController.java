package com.example.schedulejpa.schedule.controller;

import com.example.schedulejpa.global.response.Response;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<Response<List<ScheduleResponseDto>>> getSchedules() {
        return new ResponseEntity<>(Response.of(scheduleService.getSchedules()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response<ScheduleResponseDto>> createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        ;
        return new ResponseEntity<>(Response.of(scheduleService.createSchedule(requestDto)), HttpStatus.CREATED);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<Response<ScheduleResponseDto>> getSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(Response.of(scheduleService.getSchedule(scheduleId)));
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody @Valid ScheduleRequestDto requestDto) {
            scheduleService.updateSchedule(scheduleId,requestDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable Long scheduleId, @RequestBody SchedulePatchRequestDto requestDto) {
            scheduleService.updatePartialSchedule(scheduleId, requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);

        return ResponseEntity.noContent().build();
    }
}
