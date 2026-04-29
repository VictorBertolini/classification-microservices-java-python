package com.bertolini.CentralAPI.schema.user;

import com.bertolini.CentralAPI.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RemainingRequestsDTO(
        @NotNull
        Long id,
        @NotBlank
        String username,
        @NotBlank
        String email,
        @NotNull
        int requestsRemain
) {
    public RemainingRequestsDTO(User user) {
        this(user.getUserId(), user.getUsername(), user.getEmail(), user.getRequestsRemain());
    }
}
