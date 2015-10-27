package com.jslss.board.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jslss.board.entity.Board;
import com.jslss.board.entity.BoardFile;

@Transactional
public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
    public List<BoardFile> findAllByBoard(Board board);
    public List<BoardFile> findByBoard(Board board);
}