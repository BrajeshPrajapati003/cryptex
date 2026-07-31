package com.cryptex.auth.service;

import com.cryptex.auth.dto.*;
import com.cryptex.auth.entity.AppUser;
import com.cryptex.auth.exception.EmailAlreadyExistsException;
import com.cryptex.auth.mapper.AuthMapper;
import com.cryptex.auth.repository.AppUserRepository;
import com.cryptex.auth.security.entity.RefreshToken;
import com.cryptex.auth.security.jwt.JwtService;
import com.cryptex.auth.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper mapper;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public RegisterResponse register(RegisterRequest request) {

        if (repository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        AppUser user = mapper.toEntity(request);

        user.changePassword(
                passwordEncoder.encode(request.password())
        );

        AppUser saved = repository.save(user);

        return mapper.toRegisterResponse(saved);
    }

    public LoginResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        CustomUserDetails user =
                (CustomUserDetails) authentication.getPrincipal();

        String accessToken = jwtService.generateToken(user);

        RefreshToken refreshToken = refreshTokenService.create(
                user.getUser()
        );

        return new LoginResponse(
                accessToken,
                refreshToken.getToken(),
                "Bearer"
        );
    }

    public RefreshTokenResponse refresh(
            RefreshTokenRequest request
    ){

        RefreshToken oldToken = refreshTokenService
                .findByToken(request.refreshToken());

        refreshTokenService.verify(oldToken);

        AppUser user = oldToken.getUser();

        refreshTokenService.delete(oldToken);

        RefreshToken newRefreshToken =
                refreshTokenService.create(user);

        String accessToken = jwtService.generateToken(
                new CustomUserDetails(user)
        );

        return new RefreshTokenResponse(
                accessToken,
                newRefreshToken.getToken(),
                "Bearer"
        );

        /*
        Refresh Token Rotation
         */
    }

    public void logout(RefreshTokenRequest request){

        RefreshToken token =
                refreshTokenService.findByToken(
                        request.refreshToken()
                );

        refreshTokenService.delete(token);
    }
}
