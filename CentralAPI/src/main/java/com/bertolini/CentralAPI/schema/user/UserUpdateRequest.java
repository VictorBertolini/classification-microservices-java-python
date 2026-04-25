package com.bertolini.CentralAPI.schema.user;

public record UserUpdateRequest(
        String username,
        String password
) {
}
