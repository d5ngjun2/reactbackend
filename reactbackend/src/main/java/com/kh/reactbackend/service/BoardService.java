package com.kh.reactbackend.service;

import com.kh.reactbackend.dto.BoardDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BoardService {

    Long createBoard(BoardDto.Create boardCreate, MultipartFile file) throws IOException;

    List<BoardDto.Response> getAllBoards();

    void deleteBoard(Long id);

    BoardDto.Response getBoardById(Long id);

    void updateBoard(Long id, BoardDto.Update boardUpdateDto);

    void increaseViews(Long id);
}