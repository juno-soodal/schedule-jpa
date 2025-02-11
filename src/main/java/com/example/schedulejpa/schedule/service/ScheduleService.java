package com.example.schedulejpa.schedule.service;

import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.schedule.dto.SchedulePatchRequestDto;
import com.example.schedulejpa.schedule.dto.ScheduleRequestDto;
import com.example.schedulejpa.schedule.dto.ScheduleResponseDto;
import com.example.schedulejpa.schedule.entity.Schedule;
import com.example.schedulejpa.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;


    @Transactional
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        //TODO  멤버 조회
        Member member = new Member();
        Schedule schedule = new Schedule(member, requestDto.getTitle(), requestDto.getContent());
        scheduleRepository.save(schedule);
        return ScheduleResponseDto.fromSchedule(schedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getSchedules() {
        //TODO  멤버 조회
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream().map(schedule -> ScheduleResponseDto.fromSchedule(schedule)).toList();
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto getSchedule(Long scheduleId) {
        //TODO  멤버 조회
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다."));
        return ScheduleResponseDto.fromSchedule(schedule);
    }

    @Transactional
    public void updateSchedule(Long scheduleId, ScheduleRequestDto requestDto) {
        //TODO  멤버 조회
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다."));
        schedule.update(requestDto.getTitle(), requestDto.getContent());

    }




    @Transactional
    public void updatePartialSchedule(Long scheduleId, SchedulePatchRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다."));

        if (StringUtils.hasText(requestDto.getTitle())) {
            schedule.updateTitle(requestDto.getTitle());
        }

        if (StringUtils.hasText(requestDto.getContent())) {
            schedule.updateContent(requestDto.getContent());
        }

    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다."));
        scheduleRepository.delete(schedule);
    }
}
