package com.example.schedulejpa.schedule.repository;

import com.example.schedulejpa.schedule.entity.Schedule;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {

    private final EntityManager em;

    public void save(Schedule schedule) {
        em.persist(schedule);
    }

    public List<Schedule> findAll() {
        return em.createQuery("select s from Schedule s", Schedule.class)
                .getResultList();
    }

    public Optional<Schedule> findById(Long id) {
        return Optional.ofNullable(em.find(Schedule.class, id));
    }

    public void delete(Schedule schedule) {
        em.remove(schedule);
    }

}
