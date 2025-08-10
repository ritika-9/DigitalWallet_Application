package com.ritika.wallet.dto;

public class RegisterResponse {
    private Long userId;
    private String message;
    private String otp; // for demo only; in real app OTP is not returned

    public RegisterResponse(Long userId, String message, String otp) {
        this.userId = userId;
        this.message = message;
        this.otp = otp;
    }

    // getters & setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
