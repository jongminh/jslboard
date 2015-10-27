package com.jslss.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.jslss.board.entity.Role;
import com.jslss.board.entity.User;

@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {
    public List<Role> findAllByUser(User user);
}