package com.example.schedulejpa.schedule.service;

import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.schedule.dto.ScheduleRequestDto;
import com.example.schedulejpa.schedule.entity.Schedule;
import com.example.schedulejpa.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleWriter {

    private final ScheduleRepository scheduleRepository;

    public Schedule create(Member member, ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(member, requestDto.getTitle(), requestDto.getContent());
        scheduleRepository.save(schedule);
        return schedule;
    }

    public void delete(Schedule schedule) {
        scheduleRepository.deleteSchedule(schedule);
    }

    public void softDeleteSchedulesByMember(Long memberId) {
        scheduleRepository.bulkUpdateDeletedAtByMember(memberId);
    }
}
