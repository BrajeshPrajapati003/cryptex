package com.cryptex.auth.security.jwt;

import com.cryptex.auth.security.config.JwtProperties;
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
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    public String generateToken(UserDetails userDetails){

        Instant now = Instant.now();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(jwtProperties.accessExpiration())))
                .signWith(getSigningKey())
                .compact();
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



    // Use this overloaded generateToken method
    // if adding roles, permissions, tenant IDs, etc. in the future.
//    public String generateToken(Map<String, Object> claims,
//                                UserDetails userDetails){
//        return Jwts.builder()
//                .claims(claims)
//                .subject(userDetails.getUsername())
//                .issuedAt(Date.from(Instant.now()))
//                .expiration(Date.from(Instant.now().plusMillis(jwtProperties.accessExpiration())))
//                .signWith(getSigningKey())
//                .compact();
//    }

}
