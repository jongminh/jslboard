package com.jslss.board.repository;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jslss.board.entity.PasswordReset;
import com.jslss.board.entity.Role;
import com.jslss.board.entity.User;

@Transactional
public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {
    public Set<Role> findAllByUser(User user);
}