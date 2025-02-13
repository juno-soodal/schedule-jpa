package com.example.schedulejpa.schedule.service.component;

import com.example.schedulejpa.schedule.entity.Schedule;
import com.example.schedulejpa.schedule.exception.ScheduleNotFoundException;
import com.example.schedulejpa.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleFinder {

    private final ScheduleRepository scheduleRepository;


    public List<Schedule> findSchedules(int page, int size) {
        return scheduleRepository.findSchedules(page, size);
    }

    public Schedule findSchedule(Long scheduleId) {
        return scheduleRepository.findSchedule(scheduleId).orElseThrow(() -> new ScheduleNotFoundException());
    }

}
