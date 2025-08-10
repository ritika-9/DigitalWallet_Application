package com.ritika.wallet.controller;

import com.ritika.wallet.dto.RegisterRequest;
import com.ritika.wallet.dto.VerifyOtpRequest;
import com.ritika.wallet.entity.User;
import com.ritika.wallet.repo.UserRepository;
import com.ritika.wallet.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already registered"));
        }

        // Create user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setKycStatus("PENDING"); // default

        User savedUser = userRepository.save(user);

        // Generate OTP
        String otp = otpService.generateOtpForUser(savedUser.getId());

        return ResponseEntity.ok(Map.of(
                "userId", savedUser.getId(),
                "otp", otp,
                "message", "User registered successfully. Please verify using the OTP."
        ));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request) {
        boolean isValid = otpService.verifyOtp(request.getUserId(), request.getOtp());
        if (!isValid) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid or expired OTP"));
        }

        // OTP verified - KYC status remains pending
        userRepository.findById(request.getUserId()).ifPresent(user -> {
            user.setKycStatus("PENDING");
            userRepository.save(user);
        });

        return ResponseEntity.ok(Map.of("message", "OTP verified successfully."));
    }
}

