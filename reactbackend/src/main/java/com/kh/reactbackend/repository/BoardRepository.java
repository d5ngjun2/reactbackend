package com.kh.reactbackend.repository;

import com.kh.reactbackend.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    Long save(Board board);

    List<Board> findAll();

    void deleteBoard(Long id);

    Optional<Board> findById(Long id);
}
