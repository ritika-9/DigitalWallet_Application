package com.ritika.wallet.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KycUploadRequest {
    private Long userId;
    private String documentType;
    private String documentReference;
}
