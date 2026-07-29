package com.cryptex.auth.dto;

public record LoginResponse(

        String accessToken,
        String tokenType
) {
}
