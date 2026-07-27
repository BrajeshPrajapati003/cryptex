package com.cryptex.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
