package com.ritika.wallet.repo;

import com.ritika.wallet.entity.KycDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KycRepository extends JpaRepository<KycDocument, Long> {
    List<KycDocument> findByUserId(Long userId);
}
