package com.jslss.board.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jslss.board.entity.User;

@Transactional
public interface UserRepository extends JpaRepository<User, String> {
    public User findByEmail(String email);
    public User findByUserId(String userId);
}