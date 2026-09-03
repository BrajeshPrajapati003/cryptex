package com.cryptex.auth.service;

import com.cryptex.auth.entity.AppUser;
import com.cryptex.auth.exception.InvalidPasswordResetTokenException;
import com.cryptex.auth.security.config.JwtProperties;
import com.cryptex.auth.security.entity.PasswordResetToken;
import com.cryptex.auth.security.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository repository;
    private final JwtProperties jwtProperties;

    public PasswordResetToken create(AppUser user){

        // Invalidate previous reset tokens for this user
        repository.deleteByUser(user);

        SecureRandom random = new SecureRandom();

        byte[] bytes = new byte[64];
        random.nextBytes(bytes);

        String secureToken = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);

        PasswordResetToken token = PasswordResetToken.builder()
                .token(secureToken)
                .user(user)
                .expiresAt(
                        Instant.now()
                                .plus(jwtProperties.passwordResetExpiration())
                ).used(false)
                .build();

        return repository.save(token);
    }

    public PasswordResetToken findByToken(String token){

        return repository.findByToken(token)
                .orElseThrow(InvalidPasswordResetTokenException::new);
    }

    public PasswordResetToken verify(PasswordResetToken token){

        if (token.isUsed()){
            throw new InvalidPasswordResetTokenException();
        }

        if (token.getExpiresAt().isBefore(Instant.now())){
            repository.delete(token);

            throw new InvalidPasswordResetTokenException();
        }

        return token;
    }

    public void markUsed(PasswordResetToken token){

        token.markUsed();
        repository.save(token);
    }

    public void delete(PasswordResetToken token){

        repository.delete(token);
    }

    public void deleteByUser(AppUser user){
        repository.deleteByUser(user);
    }
}
