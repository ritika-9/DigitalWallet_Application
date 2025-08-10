package com.ritika.wallet.dto;

public class GenericResponse {
    private String status;
    private String message;

    public GenericResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
    // getters & setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
