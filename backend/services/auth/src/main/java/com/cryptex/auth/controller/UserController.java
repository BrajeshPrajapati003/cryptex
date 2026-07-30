package com.cryptex.auth.controller;

import com.cryptex.auth.dto.UserProfileResponse;
import com.cryptex.auth.entity.AppUser;
import com.cryptex.auth.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AppUserRepository repository;

    @GetMapping("/me")
    public UserProfileResponse me(
            Authentication authentication
    ){

        AppUser user = repository.findByEmail(
                authentication.getName()
        ).orElseThrow();

        return new UserProfileResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }

}
