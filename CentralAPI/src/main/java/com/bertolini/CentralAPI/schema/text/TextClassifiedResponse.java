package com.bertolini.CentralAPI.schema.text;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TextClassifiedResponse(
        @NotBlank
        String text,
        @NotNull
        String label,
        @NotNull
        Double score
) {
}
