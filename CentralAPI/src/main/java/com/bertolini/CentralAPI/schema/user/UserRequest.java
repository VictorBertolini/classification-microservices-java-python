package com.bertolini.CentralAPI.schema.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank(message = "Username can't be blank")
        String username,

        @NotBlank(message = "Check Email format or if it's blank")
        @Email
        String email,

        @NotBlank(message = "Password can't be blank")
        String password
) {
}
