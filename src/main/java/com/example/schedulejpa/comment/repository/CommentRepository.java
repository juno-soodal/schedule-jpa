package com.example.schedulejpa.comment.repository;

import com.example.schedulejpa.comment.entity.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public List<Comment> findComments(Long scheduleId) {

        return em.createQuery("select c from Comment c join fetch c.member m where c.schedule.id = :scheduleId", Comment.class)
                .setParameter("scheduleId", scheduleId)
                .getResultList();
    }

    public Optional<Comment> findById(Long commentId) {
        return em.createQuery("select c from Comment c join fetch c.member m where c.id = :id", Comment.class)
                .setParameter("id", commentId)
                .getResultStream()
                .findFirst();
    }

    public void save(Comment comment) {
        em.persist(comment);
    }

    public void bulkUpdateDeletedAtByMember(Long memberId) {
        em.createQuery("update Comment c set c.deletedAt = :deletedAt where c.member.id = :memberId")
                .setParameter("deletedAt", LocalDateTime.now())
                .setParameter("memberId", memberId)
                .executeUpdate();
    }

    public void deleteComment(Comment comment) {
        em.remove(comment);
    }

    public void bulkDeleteByScheduleId(Long scheduleId) {
        em.createNativeQuery("DELETE FROM comment WHERE schedule_id = :scheduleId")
                .setParameter("scheduleId", scheduleId)
                .executeUpdate();
    }
}
