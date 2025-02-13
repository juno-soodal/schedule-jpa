package com.example.schedulejpa.schedule.service;

import com.example.schedulejpa.comment.service.CommentService;
import com.example.schedulejpa.member.entity.Member;
import com.example.schedulejpa.member.repository.MemberRepository;
import com.example.schedulejpa.schedule.dto.SchedulePatchRequestDto;
import com.example.schedulejpa.schedule.dto.ScheduleRequestDto;
import com.example.schedulejpa.schedule.dto.ScheduleResponseDto;
import com.example.schedulejpa.schedule.entity.Schedule;
import com.example.schedulejpa.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    //TODO repository service로 분리
    private final MemberRepository memberRepository;

    private final CommentService commentService;


    @Transactional
    public ScheduleResponseDto createSchedule(String loginEmail, ScheduleRequestDto requestDto) {
        Member member = memberRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "없는 유저입니다."));

        Schedule schedule = new Schedule(member, requestDto.getTitle(), requestDto.getContent());
        scheduleRepository.save(schedule);
        return ScheduleResponseDto.fromSchedule(schedule);
    }

    @Transactional(readOnly = true)
    public PageImpl<ScheduleResponseDto> getSchedules(Pageable pageable) {

        Long count = scheduleRepository.count();
        List<Schedule> schedules = scheduleRepository.findSchedules(pageable);
        List<ScheduleResponseDto> dtos = schedules.stream().map(schedule -> ScheduleResponseDto.fromSchedule(schedule)).toList();

        return new PageImpl<>(dtos, pageable, count);
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto getSchedule(Long scheduleId) {

        Schedule schedule = scheduleRepository.findSchedule(scheduleId).orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다."));
        return ScheduleResponseDto.fromSchedule(schedule);
    }

    @Transactional
    public void updateSchedule(Long scheduleId, String loginEmail, ScheduleRequestDto requestDto) {

        Schedule schedule = scheduleRepository.findSchedule(scheduleId).orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다."));

        //TODO 객체지향적으로 리팩토링 필요
        //TODO JOIN FETCH 고려
        if (!schedule.getMember().getEmail().equals(loginEmail)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "수정 권한이 없습니다.");
        }
        schedule.update(requestDto.getTitle(), requestDto.getContent());

    }


    @Transactional
    public void updatePartialSchedule(Long scheduleId,String loginEmail, SchedulePatchRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findSchedule(scheduleId).orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다."));

        //TODO 객체지향적으로 리팩토링 필요
        //TODO JOIN FETCH 고려
        if (!schedule.getMember().getEmail().equals(loginEmail)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "수정 권한이 없습니다.");
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
        Schedule schedule = scheduleRepository.findSchedule(scheduleId).orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다."));


        //TODO 객체지향적으로 리팩토링 필요
        //TODO JOIN FETCH 고려
        if (!schedule.getMember().getEmail().equals(loginEmail)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "삭제 권한이 없습니다.");
        }

        commentService.deleteAllByScheduleId(schedule.getId());
        scheduleRepository.deleteSchedule(schedule);

        // 기획:수정 일자도 변경
    }

    @Transactional
    public void softDeleteSchedulesByMember(Long memberId) {
        scheduleRepository.bulkUpdateDeletedAtByMember(memberId);
    }
}
