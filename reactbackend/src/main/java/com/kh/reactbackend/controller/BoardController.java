package com.kh.reactbackend.controller;

import com.kh.reactbackend.dto.BoardDto;
import com.kh.reactbackend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity<String> createBoard(@RequestPart("boardData") BoardDto.Create boardCreate,
                                              @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            Long boardId = boardService.createBoard(boardCreate, file);
            return ResponseEntity.status(HttpStatus.CREATED).body("게시물 생성 성공. ID: " + boardId);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("파일 처리 중 오류 발생: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("게시물 생성 실패: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("게시물 생성 실패: " + e.getMessage());
        }
    }

    @GetMapping("/{id}") // 특정 게시글 상세 조회 (GET /api/board/{id})
    public ResponseEntity<BoardDto.Response> getBoardById(@PathVariable("id") Long id) {
        System.out.println("게시글 상세 조회 요청 ID: " + id);
        BoardDto.Response board = boardService.getBoardById(id); // 서비스 계층 메서드 호출
        return ResponseEntity.ok(board);
    }

    @PatchMapping("/{id}") // 게시글 수정 (PATCH /api/board/{id})
    public ResponseEntity<String> updateBoard(@PathVariable("id") Long id,
                                              @RequestBody BoardDto.Update boardUpdateDto) {
        System.out.println("게시글 수정 요청 ID: " + id);
        System.out.println("수정될 제목: " + boardUpdateDto.getBoardTitle());
        System.out.println("수정될 내용: " + boardUpdateDto.getBoardContent());

        boardService.updateBoard(id, boardUpdateDto); // 서비스 계층 메서드 호출
        return ResponseEntity.ok("게시글이 성공적으로 수정되었습니다."); // 200 OK
    }

    @GetMapping
    public ResponseEntity<List<BoardDto.Response>> getAllBoards() {
        System.out.println("모든 게시글 조회 요청");
        List<BoardDto.Response> boards = boardService.getAllBoards();
        System.out.println("총 게시글 : " + boards);
        return ResponseEntity.ok(boards);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok().build();
    }

}