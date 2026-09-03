package com.cryptex.auth.controller;

import com.cryptex.auth.dto.request.UpdateUserRequest;
import com.cryptex.auth.dto.response.UserProfileResponse;
import com.cryptex.auth.entity.AppUser;
import com.cryptex.auth.repository.AppUserRepository;
import com.cryptex.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserProfileResponse me(
            Authentication authentication
    ){

        return userService.getCurrentUser(authentication.getName());
    }

    @PatchMapping("/me")
    public UserProfileResponse updateMe(
            Authentication authentication,
            @Valid @RequestBody UpdateUserRequest request
            ){

        return userService.updateCurrentUser(
                authentication.getName(),
                request
        );
    }

}
