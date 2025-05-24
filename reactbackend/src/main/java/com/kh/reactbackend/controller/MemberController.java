package com.kh.reactbackend.controller;

import com.kh.reactbackend.dto.MemberDto;
import com.kh.reactbackend.entity.Member;
import com.kh.reactbackend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // 이 컨트롤러의 모든 API는 http://localhost:3001에서 오는 요청을 허용
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping
    public ResponseEntity<String> addMember(@RequestBody MemberDto.Create createDto) {
        String userId = memberService.createMember(createDto);
        return ResponseEntity.ok(userId);
    }

    // 로그인 (POST)
    @PostMapping("/login")
    public ResponseEntity<MemberDto.Response> login(@RequestBody MemberDto.Response loginDto) {

        MemberDto.Response loggedInUser = memberService.login(loginDto.getUserId(), loginDto.getPassword());

        return ResponseEntity.ok(loggedInUser);
    }

    // 회원정보 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteMember(@PathVariable String userId) {
        memberService.deleteMember(userId);
        return ResponseEntity.ok().build();
    }

    // 회원정보 수정
    @PatchMapping("/{userId}")
    public ResponseEntity<MemberDto.Response> updateMember(@PathVariable String userId, @RequestBody MemberDto.Update updateDto) {
        return ResponseEntity.ok(memberService.updateMember(userId, updateDto));
    }



}
