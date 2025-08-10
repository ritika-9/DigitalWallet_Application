package com.ritika.wallet.service;

import com.ritika.wallet.entity.UserOtp;
import com.ritika.wallet.repo.UserOtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final UserOtpRepository otpRepository;
    private final Random random = new Random();

    public String generateOtpForUser(Long userId) {
        String otp = String.format("%06d", random.nextInt(1_000_000));
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);

        // Remove old OTP for this user
        otpRepository.deleteByUserId(userId);

        // Save new OTP in DB
        UserOtp userOtp = UserOtp.builder()
                .userId(userId)
                .otp(otp)
                .expiryTime(expiry)
                .build();
        otpRepository.save(userOtp);

        return otp; // For demo purposes, return OTP
    }

    public boolean verifyOtp(Long userId, String otp) {
        return otpRepository.findByUserId(userId)
                .filter(o -> o.getOtp().equals(otp) && o.getExpiryTime().isAfter(LocalDateTime.now()))
                .map(o -> {
                    otpRepository.delete(o); // Delete after successful verification
                    return true;
                })
                .orElse(false);
    }
}
