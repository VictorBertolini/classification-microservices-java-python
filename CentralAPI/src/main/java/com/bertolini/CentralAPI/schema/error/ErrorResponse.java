package com.bertolini.CentralAPI.schema.error;

public record ErrorResponse(
        int status,
        String error,
        String message
) {
}
