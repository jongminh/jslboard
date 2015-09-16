package com.jslss.board.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jslss.board.entity.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
}