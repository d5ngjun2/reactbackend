package com.kh.reactbackend.entity;

import com.kh.reactbackend.enums.CommonEnums;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicInsert // insert 시 null 이 아닌 값만 필드 포함
@DynamicUpdate // 변경된 필드만 update문에 포함
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_NO") // DB의 실제 PK 컬럼명
    private Long memberNo;

    @Column(name = "USER_ID", length = 30, unique = true, nullable = false)
    private String userId;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "email", length = 254, nullable = false)
    private String email;

    @Column(name = "user_name", length = 30, nullable = false)
    private String userName;

    @Column(name = "password", length = 30, nullable = false)
    private String password;

    @Column(name = "enroll_date")
    private LocalDateTime enrollDate; // 등록일자

    @Column(length = 1, nullable = false)
    @Enumerated(EnumType.STRING)
    private CommonEnums.Status status;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    List<Board> boards = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.enrollDate = LocalDateTime.now();
        if(this.status == null) {
            this.status = CommonEnums.Status.Y;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.enrollDate = LocalDateTime.now();
    }

    public void updateMemberInfo(String userName, String email, String password, Integer age){
        if(userName != null && !userName.isEmpty()) {
            this.userName = userName;
        }
        if(email != null && !email.isEmpty()) {
            this.email = email;
        }
        if(password != null && !password.isEmpty()) {
            this.password = password;
        }
        if(age != null) {
            this.age = age;
        }
    }
}
