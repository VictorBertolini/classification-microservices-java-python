package com.bertolini.CentralAPI.schema.user;

import jakarta.validation.constraints.NotBlank;

public record UserAuthenticationData(
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
