package com.kh.reactbackend.dto;

import com.kh.reactbackend.entity.Board;
import com.kh.reactbackend.enums.CommonEnums;
import lombok.*; // AllArgsConstructor, NoArgsConstructor, Builder, Getter, Setter 포함

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BoardDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Create {
        private String title;
        private String writer;
        private String content;
        private String date;

        public Board toEntity() {
            LocalDate parsedDate = LocalDate.parse(this.date, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDateTime enrollDateTime = parsedDate.atStartOfDay();
            return Board.builder()
                    .boardTitle(this.title)
                    .boardContent(this.content)
                    .enrollDate(enrollDateTime)
                    .status(CommonEnums.Status.Y)
                    .views(0)
                    .build();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor // Builder와 함께 사용하여 모든 필드를 포함하는 생성자 자동 생성
    @NoArgsConstructor // 기본 생성자 자동 생성 (필수)
    @Builder // 빌더 패턴 사용을 위해 필요
    public static class Response {
        private Long boardNo;
        private String boardTitle;
        private String boardContent;
        private String writerName; // 게시글 작성자의 이름 (Member의 userName)
        private LocalDateTime enrollDate;
        private Integer views;
        private CommonEnums.Status status;
        private String originFileName;
        private String changedFileName;

        public static Response of(Board board) {
            return Response.builder()
                    .boardNo(board.getBoardNo())
                    .boardTitle(board.getBoardTitle())
                    .boardContent(board.getBoardContent())
                    .writerName(board.getMember() != null ? board.getMember().getUserName() : "알 수 없음")
                    .enrollDate(board.getEnrollDate())
                    .views(board.getViews())
                    .status(board.getStatus())
                    .originFileName(board.getOriginName())
                    .changedFileName(board.getChangeName())
                    .build();
        }
    }

    @Getter
    @Setter
    public static class Update {
        private String boardTitle;
        private String boardContent;
    }
}