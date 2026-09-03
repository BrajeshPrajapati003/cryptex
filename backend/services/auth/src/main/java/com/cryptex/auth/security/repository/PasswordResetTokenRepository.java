package com.cryptex.auth.security.repository;

import com.cryptex.auth.entity.AppUser;
import com.cryptex.auth.security.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, UUID> {

    Optional<PasswordResetToken> findByToken(String token);

    void deleteByUser(AppUser user);
}
