package com.cryptex.auth.dto.response;

public record LoginResponse(

        String accessToken,
        String refreshToken,
        String tokenType
) {
}
