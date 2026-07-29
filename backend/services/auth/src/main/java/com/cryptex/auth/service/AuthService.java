package com.cryptex.auth.service;

import com.cryptex.auth.dto.LoginRequest;
import com.cryptex.auth.dto.LoginResponse;
import com.cryptex.auth.dto.RegisterRequest;
import com.cryptex.auth.dto.RegisterResponse;
import com.cryptex.auth.entity.AppUser;
import com.cryptex.auth.exception.EmailAlreadyExistsException;
import com.cryptex.auth.exception.InvalidCredentialsException;
import com.cryptex.auth.exception.UserNotFoundException;
import com.cryptex.auth.mapper.AuthMapper;
import com.cryptex.auth.repository.AppUserRepository;
import com.cryptex.auth.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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

        var authentication =
                authenticationManager.authenticate(

                        new UsernamePasswordAuthenticationToken(

                                request.email(),
                                request.password()

                        )
                );

        UserDetails user =
                (UserDetails) authentication.getPrincipal();

        String accessToken =
                jwtService.generateToken(user);

        return new LoginResponse(

                accessToken,

                "Bearer"

        );
    }

}