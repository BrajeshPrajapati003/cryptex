package com.cryptex.auth.service;

import com.cryptex.auth.dto.RegisterRequest;
import com.cryptex.auth.dto.RegisterResponse;
import com.cryptex.auth.entity.AppUser;
import com.cryptex.auth.exception.EmailAlreadyExistsException;
import com.cryptex.auth.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest request){

        if(repository.existsByEmail(request.email())){
            throw new EmailAlreadyExistsException(request.email());
        }

        AppUser user = AppUser.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        AppUser saved = repository.save(user);

        return new RegisterResponse(
                saved.getId(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getEmail(),
                "Registration successful"
        );
    }
}
