package com.ritika.wallet.repo;

import com.ritika.wallet.entity.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserOtpRepository extends JpaRepository<UserOtp, Long> {
    Optional<UserOtp> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
