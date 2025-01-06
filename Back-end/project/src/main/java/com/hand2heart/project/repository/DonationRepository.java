package com.hand2heart.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hand2heart.project.model.Donation;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByUserId(Long userId);
}
