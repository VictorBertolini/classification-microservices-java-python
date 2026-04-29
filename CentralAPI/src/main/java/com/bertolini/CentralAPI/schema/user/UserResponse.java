package com.bertolini.CentralAPI.schema.user;

import com.bertolini.CentralAPI.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserResponse(
        Long id,
        String username,
        String email
) {
    public UserResponse(User user) {
        this(user.getUserId(), user.getUsername(), user.getEmail());
    }
}
