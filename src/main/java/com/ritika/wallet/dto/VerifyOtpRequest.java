package com.ritika.wallet.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpRequest {
    private Long userId;
    private String otp;
}
