package com.example.schedulejpa.schedule.service.component;

import com.example.schedulejpa.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleReader {

    private final ScheduleRepository scheduleRepository;

    public Long count() {
        return scheduleRepository.count();
    }

    public boolean existsById(Long scheduleId) {
        return scheduleRepository.existsById(scheduleId);
    }

}
