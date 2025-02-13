package com.example.schedulejpa.schedule.service;

import com.example.schedulejpa.comment.service.CommentWriter;
import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.member.exception.UnAuthorizedAccessException;
import com.example.schedulejpa.member.service.MemberReader;
import com.example.schedulejpa.schedule.dto.SchedulePatchRequestDto;
import com.example.schedulejpa.schedule.dto.ScheduleRequestDto;
import com.example.schedulejpa.schedule.dto.ScheduleResponseDto;
import com.example.schedulejpa.schedule.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final MemberReader memberReader;
    private final ScheduleWriter scheduleWriter;
    private final ScheduleReader scheduleReader;
    private final CommentWriter commentWriter;



    @Transactional
    public ScheduleResponseDto createSchedule(String loginEmail, ScheduleRequestDto requestDto) {
        Member member = memberReader.findByEmail(loginEmail);
        Schedule schedule = scheduleWriter.create(member, requestDto);
        return ScheduleResponseDto.fromSchedule(schedule);
    }

    @Transactional(readOnly = true)
    public PageImpl<ScheduleResponseDto> getSchedules(Pageable pageable) {

        Long count = scheduleReader.count();
        List<Schedule> schedules = scheduleReader.findSchedules(pageable);
        List<ScheduleResponseDto> dtos = schedules.stream().map(schedule -> ScheduleResponseDto.fromSchedule(schedule)).toList();

        return new PageImpl<>(dtos, pageable, count);
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto getSchedule(Long scheduleId) {
        Schedule schedule = scheduleReader.findSchedule(scheduleId);
        return ScheduleResponseDto.fromSchedule(schedule);
    }

    @Transactional
    public void updateSchedule(Long scheduleId, String loginEmail, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleReader.findSchedule(scheduleId);

        //TODO JOIN FETCH 고려
        if (!schedule.getMember().isSameEmail(loginEmail)) {
            throw new UnAuthorizedAccessException();
        }
        schedule.update(requestDto.getTitle(), requestDto.getContent());

    }


    @Transactional
    public void updatePartialSchedule(Long scheduleId,String loginEmail, SchedulePatchRequestDto requestDto) {
        Schedule schedule = scheduleReader.findSchedule(scheduleId);

        //TODO JOIN FETCH 고려
        if (!schedule.getMember().isSameEmail(loginEmail)) {
            throw new UnAuthorizedAccessException();
        }

        if (StringUtils.hasText(requestDto.getTitle())) {
            schedule.updateTitle(requestDto.getTitle());
        }

        if (StringUtils.hasText(requestDto.getContent())) {
            schedule.updateContent(requestDto.getContent());
        }

    }

    @Transactional
    public void deleteSchedule(Long scheduleId, String loginEmail) {

        Schedule schedule = scheduleReader.findSchedule(scheduleId);


        //TODO JOIN FETCH 고려
        if (!schedule.getMember().isSameEmail(loginEmail)) {
            throw new UnAuthorizedAccessException();
        }

        commentWriter.deleteAllByScheduleId(schedule.getId());
        scheduleWriter.delete(schedule);

    }
}
