package com.kh.reactbackend.repository;

import com.kh.reactbackend.entity.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepositoryImpl implements BoardRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long save(Board board) {
        em.persist(board);
        return board.getBoardNo();
    }

    @Override
    public List<Board> findAll() {
        return em.createQuery("SELECT b FROM Board b ORDER BY b.boardNo DESC", Board.class)
                .getResultList();
    }

    @Override
    @Transactional
    public void deleteBoard(Long id) {
        em.remove(em.find(Board.class, id));
    }

    @Override
    public Optional<Board> findById(Long id) {
        Board board = em.find(Board.class, id);
        return Optional.ofNullable(board);
    }

    @Override
    public int incrementViews(Long id) {
        String jpql = "UPDATE Board b SET b.views = COALESCE(b.views, 0) + 1 WHERE b.boardNo = :id";
        return em.createQuery(jpql)
                .setParameter("id", id)
                .executeUpdate(); // 업데이트된 행의 수를 반환
    }
}
