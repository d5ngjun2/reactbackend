package com.kh.reactbackend.entity;

import com.kh.reactbackend.enums.CommonEnums;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicInsert // insert 시 null 이 아닌 값만 필드 포함
@DynamicUpdate // 변경된 필드만 update문에 포함
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private Long boardNo;

    //Board : Member : N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_NO", nullable = false)
    private Member member;

    @Column(name = "board_title", nullable = false)
    private String boardTitle;

    @Column(name = "board_content", nullable = false)
    @Lob // 대용량 매핑
    private String boardContent;

    @Column(name = "origin_file_name") // DB 컬럼명 지정
    private String originName;

    @Column(name = "changed_file_name") // DB 컬럼명 지정
    private String changeName;

    @Column(name = "date", nullable = false)
    private LocalDateTime enrollDate;

    @Column(length = 1, nullable = false)
    @Enumerated(EnumType.STRING)
    private CommonEnums.Status status;

    public void changeMember(Member member) {
        this.member = member;
        if(!member.getBoards().contains(this)) {
            member.getBoards().add(this);
        }
    }

    public void changeFile(String originName, String changeName) {
        this.originName = originName;
        this.changeName = changeName;
    }

    @Column(name = "views", nullable = false)
    private Integer views = 0;

    public void setViews(int views) {
        this.views = views;
    }
    public void incrementViews() {
        if (this.views == null) {
            this.views = 0;
        }
        this.views++;
    }

    public void updateContent(String boardTitle, String boardContent) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }
}
