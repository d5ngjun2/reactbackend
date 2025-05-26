package com.kh.reactbackend.service;

import com.kh.reactbackend.dto.BoardDto;
import com.kh.reactbackend.entity.Board;
import com.kh.reactbackend.entity.Member;
import com.kh.reactbackend.repository.BoardRepository;
import com.kh.reactbackend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    private final String UPLOAD_PATH = "C:\\workspace\\reactbackgit\\files";

    @Override
    public Long createBoard(BoardDto.Create boardCreate, MultipartFile file) throws IOException {

        Member member = memberRepository.findByUserName(boardCreate.getWriter())
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 존재하지 않습니다."));

        System.out.println("현재 아이디 : " + boardCreate.getWriter());

        String originalName = null;
        String changeName = null;

        // 2. 파일 처리 로직
        if (file != null && !file.isEmpty()) {
            originalName = file.getOriginalFilename();
            changeName = UUID.randomUUID().toString() + "_" + originalName;

            File uploadDir = new File(UPLOAD_PATH);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            file.transferTo(new File(UPLOAD_PATH + File.separator + changeName));
        }

        // 3. DTO를 Board 엔티티로 변환 및 관계 설정
        Board board = boardCreate.toEntity();
        board.changeMember(member); // Board 엔티티와 Member 엔티티 연결
        board.changeFile(originalName, changeName); // Board 엔티티에 파일 이름 정보 저장

        // 4. 저장된 게시글의 ID 반환
        return boardRepository.save(board);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardDto.Response> getAllBoards() {
        List<Board> boards = boardRepository.findAll();

        return boards.stream()
                .map(BoardDto.Response::of)
                .collect(Collectors.toList()); // List로 수집
    }

    @Override
    @Transactional
    public BoardDto.Response getBoardById(Long id) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. 게시글 번호: " + id));

        return BoardDto.Response.of(board);
    }

    @Override
    public void updateBoard(Long id, BoardDto.Update boardUpdateDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. 게시글 번호: " + id));

        board.updateContent(boardUpdateDto.getBoardTitle(), boardUpdateDto.getBoardContent());
    }

    @Override
    @Transactional
    public void increaseViews(Long id) {
       int updateCount = boardRepository.incrementViews(id);
        System.out.println("increaseViews가 잘 실행중이야 : " + updateCount);
       if (updateCount == 0) {
           throw new IllegalArgumentException("게시글이 존재하지 않습니다. 게시글 번호 : " + id);
       }
    }


    @Override
    public void deleteBoard(Long id) {
        boardRepository.deleteBoard(id);
    }

}