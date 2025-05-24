package com.kh.reactbackend.dto;

import com.kh.reactbackend.entity.Member;
import lombok.*;

public class MemberDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
            private String userId;
            private String userName;
            private String email;
            private Integer age;
            private String password;

        public static Response toDto(Member member) {
            return Response.builder()
                    .userId(member.getUserId())
                    .userName(member.getUserName())
                    .email(member.getEmail())
                    .age(member.getAge())
                    .build();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Update {
        private String userId;
        private String userName;
        private String email;
        private Integer age;
        private String password;

        public static Update toDto(Member member) {
            return Update.builder()
                    .userId(member.getUserId())
                    .userName(member.getUserName())
                    .email(member.getEmail())
                    .age(member.getAge())
                    .build();
        }
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Create{
        private String userId;
        private String password;
        private String userName;
        private String email;
        private Integer age;

        public Member toEntity() {
            return Member.builder()
                    .userId(this.userId)
                    .password(this.password)
                    .userName(this.userName)
                    .email(this.email)
                    .age(this.age)
                    .build();
        }
    }
}
