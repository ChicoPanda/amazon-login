package com.acme2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acme2.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

