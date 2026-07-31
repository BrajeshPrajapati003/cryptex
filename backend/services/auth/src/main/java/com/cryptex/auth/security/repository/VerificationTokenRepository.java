package com.cryptex.auth.security.repository;

import com.cryptex.auth.entity.AppUser;
import com.cryptex.auth.security.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {

    Optional<VerificationToken> findByToken(String token);

    void deleteByUser(AppUser user);
}
