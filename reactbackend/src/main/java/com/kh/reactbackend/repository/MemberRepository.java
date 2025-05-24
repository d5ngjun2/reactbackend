package com.kh.reactbackend.repository;

import com.kh.reactbackend.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    void save(Member member);

    Optional<Member> findById(String userId);

    Optional<Member> findByUserId(String userId);

    Optional<Member> findByUserName(String userName);

    void delete(Member member);
}
