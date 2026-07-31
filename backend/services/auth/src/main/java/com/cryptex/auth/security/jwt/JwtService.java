package com.cryptex.auth.security.jwt;

import com.cryptex.auth.security.config.JwtProperties;
import com.cryptex.auth.security.user.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();

        if (userDetails instanceof CustomUserDetails customUser){

            claims.put(
                    "userId",
                    customUser.getUser().getId()
            );

            claims.put(
                    "role",
                    customUser.getUser().getRole().name()
            );
        }

        return buildToken(claims, userDetails);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ){

        return extractUsername(token).equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(Date.from(Instant.now()));
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private <T>T extractClaim(
            String token,
            Function<Claims, T> resolver
    ){

        return resolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token){

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey(){
        byte[] key = Decoders.BASE64.decode(jwtProperties.secret());
        return Keys.hmacShaKeyFor(key);
    }

    private String buildToken(
            Map<String, Object> claims,
            UserDetails userDetails
    ){

        Instant now = Instant.now();

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(
                        now.plus(jwtProperties.accessExpiration())
                ))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractRole(String token){
        return extractClaim(token,
                claims -> claims.get("role", String.class));
    }

    public UUID extractUserId(String token){

        String id = extractClaim(
                token,
                claims -> claims.get("userId", String.class)
        );

        return UUID.fromString(id);
    }

}
