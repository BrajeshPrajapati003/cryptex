package com.cryptex.auth.dto.response;

import com.cryptex.auth.enums.Role;

import java.util.UUID;

public record UserProfileResponse(

        UUID id,
        String firstName,
        String lastname,
        String email,
        Role role
) {
}
