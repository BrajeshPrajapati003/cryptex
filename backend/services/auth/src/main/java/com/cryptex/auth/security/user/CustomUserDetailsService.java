package com.cryptex.auth.security.user;

import com.cryptex.auth.exception.UserNotFoundException;
import com.cryptex.auth.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) {

        return repository.findByEmail(email)
                .map(CustomUserDetails::new)
                .orElseThrow(() ->
                        new UserNotFoundException(email));
    }
}
