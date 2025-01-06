package com.hand2heart.project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand2heart.project.model.user;
import com.hand2heart.project.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public user register(user user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email sudah digunakan.");
        }
        return userRepository.save(user);
    }

    public Optional<user> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<user> findById(Long id) {
        return userRepository.findById(id);
    }
    public user save(user user) {
        return userRepository.save(user);
    }
}
