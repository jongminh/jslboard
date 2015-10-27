package com.jslss.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.jslss.board.entity.Board;
import com.jslss.board.entity.User;

@Transactional
public interface BoardRepository extends JpaRepository<Board, Long> {
    public List<Board> findAllByUser(User user);
}