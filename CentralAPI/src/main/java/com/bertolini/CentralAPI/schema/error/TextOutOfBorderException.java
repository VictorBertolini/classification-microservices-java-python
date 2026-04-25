package com.bertolini.CentralAPI.schema.error;

public class TextOutOfBorderException extends RuntimeException {
    public TextOutOfBorderException(int limit) {
        super("Text Out of Border -> limit to " + limit + " characters");
    }
}
