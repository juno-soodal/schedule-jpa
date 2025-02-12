package com.example.schedulejpa.schedule.repository;

import com.example.schedulejpa.schedule.entity.Schedule;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {

    private final EntityManager em;

    public void save(Schedule schedule) {
        em.persist(schedule);
    }

    public List<Schedule> findSchedules() {
        return em.createQuery("select s from Schedule s join fetch s.member m", Schedule.class)
                .getResultList();
    }

    public Optional<Schedule> findSchedule(Long id) {
        return Optional.ofNullable(em.find(Schedule.class, id));
    }

    public void delete(Schedule schedule) {
        em.remove(schedule);
    }

    public void bulkUpdateDeletedAtByMember(Long memberId) {
        em.createQuery("update Schedule s set s.deletedAt = :deletedAt where s.member.id = :memberId")
                .setParameter("deletedAt", LocalDateTime.now())
                .setParameter("memberId", memberId)
                .executeUpdate();
    }

    public boolean existsById(Long scheduleId) {
        return em.createQuery("select s from Schedule s WHERE s.id = :id", Schedule.class)
                .setParameter("id", scheduleId)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .isPresent();
    }

    public void deleteSchedule(Schedule schedule) {
        em.remove(schedule);
    }
}
