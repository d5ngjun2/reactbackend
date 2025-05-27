package com.kh.reactbackend.service;

import com.kh.reactbackend.dto.MemberDto;
import com.kh.reactbackend.entity.Member;
import com.kh.reactbackend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public String createMember(MemberDto.Create createDto) {
        Member member = createDto.toEntity();
        memberRepository.save(member);
        return member.getUserId();
    }

    @Transactional
    @Override
    public MemberDto.Response login(String userId, String userPwd) {
        System.out.println("현재 아이디 : " + userId);
        System.out.println("현재 비밀번호 : " + userPwd);
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("아이디를 찾을 수 없습니다."));

        if(!member.getPassword().equals(userPwd)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return MemberDto.Response.toDto(member);
    }

    @Override
    public void deleteMember(String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        memberRepository.delete(member);
    }

    @Override
    @Transactional
    public MemberDto.Response updateMember(String userId, MemberDto.Update updateDto) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        member.updateMemberInfo(
                updateDto.getUserName(),
                updateDto.getEmail(),
                updateDto.getPassword(),
                updateDto.getAge()
        );
        return MemberDto.Response.toDto(member);
    }
}
