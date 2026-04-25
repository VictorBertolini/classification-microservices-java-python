package com.bertolini.CentralAPI.schema.error;

import org.springframework.validation.FieldError;

public record ErrorResponse(
        int status,
        String error,
        String message
) {
}
