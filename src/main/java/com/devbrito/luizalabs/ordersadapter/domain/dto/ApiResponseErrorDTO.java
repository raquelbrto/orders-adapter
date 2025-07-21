package com.devbrito.luizalabs.ordersadapter.domain.dto;

import org.springframework.http.HttpStatus;

public record ApiResponseErrorDTO(
        String error,
        String message,
        String timestamp
) {
    public ApiResponseErrorDTO(HttpStatus status, String message) {
        this(status.getReasonPhrase(), message, java.time.OffsetDateTime.now().toString());
    }
}
