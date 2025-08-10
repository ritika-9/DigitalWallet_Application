package com.ritika.wallet.controller;

import com.ritika.wallet.entity.Wallet;
import com.ritika.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getWallet(@PathVariable Long userId) {
        return ResponseEntity.ok(walletService.getWalletByUserId(userId));
    }

    @PostMapping("/deposit/{userId}")
    public ResponseEntity<?> deposit(@PathVariable Long userId, @RequestParam BigDecimal amount) {
        Wallet wallet = walletService.deposit(userId, amount);
        return ResponseEntity.ok(Map.of("message", "Deposit successful", "balance", wallet.getBalance()));
    }

    @PostMapping("/withdraw/{userId}")
    public ResponseEntity<?> withdraw(@PathVariable Long userId, @RequestParam BigDecimal amount) {
        Wallet wallet = walletService.withdraw(userId, amount);
        return ResponseEntity.ok(Map.of("message", "Withdrawal successful", "balance", wallet.getBalance()));
    }
}
