package com.cryptex.auth.controller;

import com.cryptex.auth.dto.LoginRequest;
import com.cryptex.auth.dto.LoginResponse;
import com.cryptex.auth.dto.RegisterRequest;
import com.cryptex.auth.dto.RegisterResponse;
import com.cryptex.auth.service.AuthService;
import com.cryptex.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
