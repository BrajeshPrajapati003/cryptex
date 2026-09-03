package com.cryptex.auth.service;

import com.cryptex.auth.dto.request.UpdateUserRequest;
import com.cryptex.auth.dto.response.UserProfileResponse;
import com.cryptex.auth.entity.AppUser;
import com.cryptex.auth.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepository repository;

    @Transactional(readOnly = true)
    public UserProfileResponse getCurrentUser(String email){

        AppUser user = repository.findByEmail(email)
                .orElseThrow();

        return toResponse(user);
    }

    @Transactional
    public UserProfileResponse updateCurrentUser(
            String email,
            UpdateUserRequest request
    ){

        AppUser user = repository.findByEmail(email)
                .orElseThrow();

        user.updateProfile(
                request.firstName(),
                request.lastName()
        );

        user = repository.save(user);

        return toResponse(user);
    }

    private UserProfileResponse toResponse(AppUser user){

        return new UserProfileResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
