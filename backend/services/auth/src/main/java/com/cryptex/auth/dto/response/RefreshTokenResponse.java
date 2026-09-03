package com.cryptex.auth.dto.response;

public record RefreshTokenResponse(

        String accessToken,
        String refreshToken, // refresh token rotation
        String tokenType
) {
}
