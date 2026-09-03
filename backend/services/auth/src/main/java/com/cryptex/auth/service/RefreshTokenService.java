package com.cryptex.auth.service;

import com.cryptex.auth.entity.AppUser;
import com.cryptex.auth.exception.InvalidRefreshTokenException;
import com.cryptex.auth.security.config.JwtProperties;
import com.cryptex.auth.security.entity.RefreshToken;
import com.cryptex.auth.security.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final JwtProperties jwtProperties;

    public RefreshToken create(AppUser user){

        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[64];
        random.nextBytes(bytes);
        String secureToken = Base64.getUrlEncoder()
                .withoutPadding().
                encodeToString(bytes);

        RefreshToken token = RefreshToken.builder()
//                .token(UUID.randomUUID().toString())
                .token(secureToken)
                .user(user)
                .expiresAt(
                        Instant.now()
                                .plus(jwtProperties.refreshExpiration())
                )
                .revoked(false)
                .build();

        return repository.save(token);

        /*
          Generate refresh token and store in the DB.
         */
    }

    public RefreshToken findByToken(String token){

        return repository.findByToken(token)
                .orElseThrow(()->
                        new InvalidRefreshTokenException("Invalid refresh token"));
    }

    public RefreshToken verify(RefreshToken token){

        if (token.isRevoked()){
            throw new InvalidRefreshTokenException("Refresh token revoked");
        }

        if (token.getExpiresAt().isBefore(Instant.now())){

            repository.delete(token);

            throw new InvalidRefreshTokenException("Refresh token expired");
        }

        /*
         Expired tokens are automatically removed.
         */
        return token;
    }

    /*
    Revoke ONE session
     */
    public void revoke(RefreshToken token){
        token.revoke();
        repository.save(token);

        /*
        Use during logout.
         */
    }

    /*
    Revoke ALL sessions
    Laptop refresh token → revoked=true
    Phone refresh token  → revoked=true
    Tablet refresh token → revoked=true
     */
    public void revokeAllByUser(AppUser user){

        List<RefreshToken> tokens = repository.findAllByUser(user);

        tokens.forEach(RefreshToken::revoke);

        repository.saveAll(tokens);
    }

    public void delete(RefreshToken token){
        repository.delete(token);
    }

}
