package com.example.schedulejpa.schedule.repository;

import com.example.schedulejpa.schedule.entity.Schedule;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {

    private final EntityManager em;

    public Long save(Schedule schedule) {

        em.persist(schedule);

        return schedule.getId();
    }

    public List<Schedule> findAll() {
        return em.createQuery("select s from Schedule s", Schedule.class)
                .getResultList();
    }

    public void update(Long scheduleId, String title, String content) {
        Schedule schedule = em.find(Schedule.class, scheduleId);
        schedule.updateSchedule(title, content);
    }

    public void delete(Long scheduleId) {
        Schedule schedule = em.find(Schedule.class, scheduleId);
        em.remove(schedule);
    }

}
