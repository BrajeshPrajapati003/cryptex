package com.cryptex.auth.dto;

import java.util.UUID;

public record RegisterResponse(

        UUID id,
        String firstName,
        String lastname,
        String email,
        String message
) {
}
