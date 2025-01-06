package com.hand2heart.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hand2heart.project.model.user;

public interface UserRepository extends JpaRepository<user, Long> {
    Optional<user> findByEmail(String email);
}
