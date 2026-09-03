package com.cryptex.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(

        @NotBlank @Size(max = 50)
        String firstName,

        @NotBlank @Size(max = 50)
        String lastName
) {
}
