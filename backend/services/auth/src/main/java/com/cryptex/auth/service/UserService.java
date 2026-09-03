package com.cryptex.auth.service;

import com.cryptex.auth.entity.AppUser;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    public AppUser getCurrentUser(UUID userId){

    }

    public UserResponse updateCurrentUser(
            UUID userId,
            UpdateUserRequest request
    ){

    }
}
