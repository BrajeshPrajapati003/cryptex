package com.cryptex.auth.dto;

public record RefreshTokenResponse(

        String accessToken,
        String refreshToken, // refresh token rotation
        String tokenType
) {
}
