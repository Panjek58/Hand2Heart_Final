package com.hand2heart.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hand2heart.project.model.Donation;
import com.hand2heart.project.service.DonationService;

@RestController
@RequestMapping("/api/donation")
@CrossOrigin(origins = "http://localhost:3000")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @PostMapping
    public ResponseEntity<?> makeDonation(@RequestBody Donation donation) {
    try {
        System.out.println("Donation data received: " + donation); // Debug input
        donationService.processDonation(donation);
        return ResponseEntity.ok("Donasi berhasil!");
    } catch (IllegalArgumentException e) {
        System.err.println("Validation error: " + e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
        e.printStackTrace(); // Cetak log lengkap untuk error lainnya
        return ResponseEntity.status(500).body("Terjadi kesalahan di server.");
    }
}

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Donation>> getUserDonations(@PathVariable Long userId) {
        List<Donation> donations = donationService.getDonationsByUserId(userId);
        return ResponseEntity.ok(donations);
    }
}   