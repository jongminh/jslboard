package com.jslss.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.jslss.board.entity.BoardFile;
import com.jslss.board.entity.User;

@Transactional
public interface UserRepository extends JpaRepository<User, String> {
    public User findByEmail(String email);
    public User findByUserId(String userId);
    
//    @Query("SELECT f FROM board b, file f where b.id = f.board_id")
//    public Set<BoardFile> findBoardFileSetByUserId(String userId);
    
//    @Query("SELECT f FROM User u, Board b, BoardFile f where u.id = :id and u.id = b.user_id and b.id = f.board_id") 
//    public List<BoardFile> findBoardFileSetByUserId(@Param("id") String id);
}