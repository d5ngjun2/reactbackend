package com.kh.reactbackend.service;

import com.kh.reactbackend.dto.MemberDto;

public interface MemberService {

    String createMember(MemberDto.Create createDto);

    MemberDto.Response login(String userId, String userPwd);

    void deleteMember(String userId);

    MemberDto.Response updateMember(String userId, MemberDto.Update updateDto);
}
