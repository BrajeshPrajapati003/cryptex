package com.cryptex.auth.controller;

import com.cryptex.auth.dto.request.*;
import com.cryptex.auth.dto.response.LoginResponse;
import com.cryptex.auth.dto.response.RefreshTokenResponse;
import com.cryptex.auth.dto.response.RegisterResponse;
import com.cryptex.auth.service.AuthService;
import com.cryptex.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @Valid @RequestBody RegisterRequest request
            ){

        RegisterResponse response = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "User registered successfully.",
                        response
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest loginRequest
            ){

        LoginResponse response = authService.login(loginRequest);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Login successful",
                        response
                )
        );
    }

    @PostMapping("/refresh")
    public RefreshTokenResponse refresh(
            @RequestBody RefreshTokenRequest request
    ){

        return authService.refresh(request);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(
            @RequestBody RefreshTokenRequest request
    ){

        authService.logout(request);
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<Void>> verifyEmail(
            @RequestParam String token
    ){

        authService.verifyEmail(token);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Email verified successfully.",
                        null
                )
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request
            ){

        authService.resetPassword(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Password reset successfully.",
                        null
                )
        );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request
            ){

        authService.forgotPassword(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "If the email exists, a password reset link has been sent.",
                        null
                )
        );
    }
}
