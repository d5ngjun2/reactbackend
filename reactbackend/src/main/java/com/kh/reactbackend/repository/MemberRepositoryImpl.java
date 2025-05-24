package com.kh.reactbackend.repository;

import com.kh.reactbackend.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void save(Member member) {
        em.persist(member);
    }

    public Optional<Member> findById(String userId) {
        return Optional.ofNullable(em.find(Member.class, userId));
    }

    @Override
    public Optional<Member> findByUserId(String userId) {
        try {
            // JPQL을 사용하여 userId 컬럼으로 조회
            TypedQuery<Member> query = em.createQuery(
                    "SELECT m FROM Member m WHERE m.userId = :userId", Member.class);
            query.setParameter("userId", userId);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            // 결과가 없는 경우 (회원을 찾지 못한 경우) Optional.empty() 반환
            return Optional.empty();
        }
    }

    @Override
    public Optional<Member> findByUserName(String userName) {
        try {
            // JPQL을 사용하여 userId 컬럼으로 조회
            TypedQuery<Member> query = em.createQuery(
                    "SELECT m FROM Member m WHERE m.userName = :userName", Member.class);
            query.setParameter("userName", userName);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            // 결과가 없는 경우 (회원을 찾지 못한 경우) Optional.empty() 반환
            return Optional.empty();
        }
    }

    @Override
    public void delete(Member member) {
        em.remove(member);
    }

}
