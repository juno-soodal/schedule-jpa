package com.example.schedulejpa.member.repository;

import com.example.schedulejpa.member.entity.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public List<Member> findMembers() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    public void delete(Member member) {
        em.remove(member);
    }

    public boolean existsByEmail(String email) {

        return em.createNativeQuery("SELECT * FROM member WHERE email = :email",Member.class)
                .setParameter("email", email)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .isPresent();
    }

    public Optional<Member> findByEmail(String email) {
        return em.createQuery("select m from Member m where email = :email", Member.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }
}
