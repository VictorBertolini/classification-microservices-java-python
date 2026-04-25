package com.bertolini.CentralAPI.schema.text;

import jakarta.validation.constraints.NotBlank;

public record TextClassifyRequest(
        @NotBlank(message = "Text can't be blank")
        String text
) {
}
