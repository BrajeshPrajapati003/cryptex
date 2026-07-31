package com.cryptex.auth.service;

import com.cryptex.auth.entity.AppUser;
import com.cryptex.auth.security.entity.VerificationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    VerificationToken create(AppUser user){}

    VerificationToken findByToken(String token){}

    VerificationToken verify(VerificationToken token){}

    void markUsed(VerificationToken token){}

    void delete(VerificationToken token){}
}
