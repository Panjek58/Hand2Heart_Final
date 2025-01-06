package com.hand2heart.project.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hand2heart.project.model.user;
import com.hand2heart.project.service.DonationService;
import com.hand2heart.project.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DonationService donationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody user user) {
        try {
            user registeredUser = userService.register(user);
            return ResponseEntity.ok(registeredUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        Optional<user> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "id", userOptional.get().getId(),
                "username", userOptional.get().getUsername(),
                "role", userOptional.get().getRole()
            ));
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id) {
    try {
        Optional<user> userOptional = userService.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        user user = userOptional.get();

        // Format donasi dengan validasi
        List<Map<String, Object>> formattedDonations = donationService.getDonationsByUserId(id).stream()
                .map(donation -> {
                    try {
                        return Map.<String, Object>of(
                            "id", donation.getId(),
                            "orphanageName", donation.getPantiAsuhan() != null ? donation.getPantiAsuhan().getNamaPanti() : "Unknown",
                            "date", donation.getDate() != null ? donation.getDate().toString() : "Unknown",
                            "amount", Optional.ofNullable(donation.getAmount()).orElse(0),
                            "item", donation.getItem() != null ? donation.getItem() : "Unknown"
                        );
                    } catch (Exception e) {
                        // Log jika terjadi error
                        System.err.println("Error formatting donation: " + e.getMessage());
                        return Map.<String, Object>of();
                    }
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(Map.of(
            "user", user, // Kirim data user langsung
            "donations", formattedDonations // Format donasi langsung
        ));
    } catch (Exception e) {
        // Log the error
        System.err.println("An error occurred while fetching the profile: " + e.getMessage());
        return ResponseEntity.status(500).body("An error occurred while fetching the profile.");
    }
    }
  @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody user updatedUserDetails) {
        try {
            Optional<user> userOptional = userService.findById(id);
            if (!userOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            user existingUser = userOptional.get();
            // Update fields yang ingin diupdate
            if (updatedUserDetails.getUsername() != null) existingUser.setUsername(updatedUserDetails.getUsername());
            if (updatedUserDetails.getEmail() != null) existingUser.setEmail(updatedUserDetails.getEmail());
            // Tambahkan field lain yang mungkin ingin diupdate

            user updatedUser = userService.save(existingUser);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the user.");
        }
    }

  

}
