package com.jslss.board.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jslss.board.entity.Role;
import com.jslss.board.entity.User;

@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {
    public User findByUser(User user);
    public User findByRole(String role);
}