package com.jslss.board.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jslss.board.entity.Board;
import com.jslss.board.entity.Comment;
import com.jslss.board.entity.User;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findAllByUser(User user);
    public List<Comment> findAllByBoard(Board board);
}