package com.bertolini.CentralAPI.schema.user;

import com.bertolini.CentralAPI.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdateRoleRequest(
        @Email
        @NotBlank
        String email,
        @NotNull
        UserRole role
) {
}
