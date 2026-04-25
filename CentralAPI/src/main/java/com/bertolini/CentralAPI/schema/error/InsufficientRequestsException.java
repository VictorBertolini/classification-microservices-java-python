package com.bertolini.CentralAPI.schema.error;

public class InsufficientRequestsException extends RuntimeException {
    public InsufficientRequestsException(String message) {
        super(message);
    }
}
