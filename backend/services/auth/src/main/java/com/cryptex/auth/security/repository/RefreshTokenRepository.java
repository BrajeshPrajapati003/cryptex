package com.cryptex.auth.security.repository;

import com.cryptex.auth.entity.AppUser;
import com.cryptex.auth.security.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByToken(String token);

    // Support of multiple devices
    List<RefreshToken> findAllByUser(AppUser user);

    void deleteByUser(AppUser user);

}
