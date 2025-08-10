package com.ritika.wallet.service;

import com.ritika.wallet.dto.RegisterRequest;
import com.ritika.wallet.entity.User;
import com.ritika.wallet.exception.ResourceAlreadyExistsException;
import com.ritika.wallet.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, OtpService otpService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
    }

    @Transactional
    public String registerUser(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new ResourceAlreadyExistsException("User with this email already exists");
        }
        if (userRepository.existsByPhone(req.getPhone())) {
            throw new ResourceAlreadyExistsException("User with this phone already exists");
        }

        User user = new User(req.getName(), req.getEmail(), req.getPhone(),
                passwordEncoder.encode(req.getPassword()), "USER", "PENDING");
        userRepository.save(user);

        // generate OTP
        return otpService.generateOtpForUser(user.getId());
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public void markKycStatus(Long userId, String status) {
        userRepository.findById(userId).ifPresent(u -> {
            u.setKycStatus(status);
            userRepository.save(u);
        });
    }
}
