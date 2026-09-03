package com.cryptex.auth.service;

import com.cryptex.auth.client.NotificationClient;
import com.cryptex.auth.dto.request.*;
import com.cryptex.auth.dto.response.LoginResponse;
import com.cryptex.auth.dto.response.RefreshTokenResponse;
import com.cryptex.auth.dto.response.RegisterResponse;
import com.cryptex.auth.entity.AppUser;
import com.cryptex.auth.exception.EmailAlreadyExistsException;
import com.cryptex.auth.exception.InvalidCredentialsException;
import com.cryptex.auth.mapper.AuthMapper;
import com.cryptex.auth.repository.AppUserRepository;
import com.cryptex.auth.security.entity.PasswordResetToken;
import com.cryptex.auth.security.entity.RefreshToken;
import com.cryptex.auth.security.entity.VerificationToken;
import com.cryptex.auth.security.jwt.JwtService;
import com.cryptex.auth.security.user.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper mapper;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    private final VerificationTokenService verificationTokenService;
    private final PasswordResetTokenService passwordResetTokenService;

    private final NotificationClient notificationClient;

    public RegisterResponse register(RegisterRequest request) {

        if (repository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        AppUser user = mapper.toEntity(request);

        user.changePassword(
                passwordEncoder.encode(request.password())
        );

        AppUser saved = repository.save(user);

        VerificationToken verificationToken =
                verificationTokenService.create(saved);

        String verificationLink =
                "http://localhost:3000/verify?token="
                + verificationToken.getToken();

        SendNotificationRequest notificationRequest =
                new SendNotificationRequest(
                        saved.getEmail(),
                        "EMAIL_VERIFICATION",
                        Map.of(
                                "name", saved.getFirstName(),
                                "verificationLink", verificationLink
                        )
                );

        notificationClient.sendNotification(notificationRequest);

        return mapper.toRegisterResponse(saved);
    }

    /**
     * Spring Security
     *       │
     *       │ BadCredentialsException
     *       ▼
     * AuthService
     *       │
     *       │ InvalidCredentialsException
     *       ▼
     * GlobalExceptionHandler
     *       │
     *       ▼
     * HTTP 401
     */
    public LoginResponse login(LoginRequest request) {

        try{
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.email(),
                                    request.password()
                            )
                    );

            CustomUserDetails user =
                    (CustomUserDetails) authentication.getPrincipal();

            String accessToken = jwtService.generateToken(user);

            RefreshToken refreshToken = refreshTokenService.create(
                    user.getUser()
            );

            return new LoginResponse(
                    accessToken,
                    refreshToken.getToken(),
                    "Bearer"
            );
        }catch (BadCredentialsException ex){
            throw new InvalidCredentialsException();
        }
    }

    public RefreshTokenResponse refresh(RefreshTokenRequest request){

        RefreshToken oldToken = refreshTokenService
                .findByToken(request.refreshToken());

        refreshTokenService.verify(oldToken);

        AppUser user = oldToken.getUser();

        refreshTokenService.delete(oldToken);

        RefreshToken newRefreshToken =
                refreshTokenService.create(user);

        String accessToken = jwtService.generateToken(
                new CustomUserDetails(user)
        );

        return new RefreshTokenResponse(
                accessToken,
                newRefreshToken.getToken(),
                "Bearer"
        );

        /*
        Refresh Token Rotation
         */
    }

    public void logout(RefreshTokenRequest request){

        RefreshToken token =
                refreshTokenService.findByToken(
                        request.refreshToken()
                );

        refreshTokenService.delete(token);
    }

    @Transactional
    public void verifyEmail(String token){

        VerificationToken verificationToken =
                verificationTokenService.findByToken(token);

        verificationTokenService.verify(verificationToken);

        AppUser user = verificationToken.getUser();

        user.enable();

        verificationTokenService.markUsed(verificationToken);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request){

        PasswordResetToken resetToken =
                passwordResetTokenService.findByToken(request.token());

        passwordResetTokenService.verify(resetToken);

        AppUser user = resetToken.getUser();

        user.changePassword(
                passwordEncoder.encode(request.newPassword())
        );

        refreshTokenService.revokeAllByUser(user);

        passwordResetTokenService.markUsed(resetToken);
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequest request){

        repository.findByEmail(request.email())
                .ifPresent(user -> {

                    // Invalidate any previous reset tokens
                    passwordResetTokenService.deleteByUser(user);

                    PasswordResetToken resetToken =
                            passwordResetTokenService.create(user);

                    String resetLink =
                            "http://localhost:3000/reset-password?token="
                            + resetToken.getToken();

                    SendNotificationRequest notificationRequest =
                            new SendNotificationRequest(
                            user.getEmail(),
                            "PASSWORD_RESET",
                            Map.of(
                                    "name", user.getFirstName(),
                                    "resetLink", resetLink
                            )
                    );

                    notificationClient.sendNotification(notificationRequest);
                });
    }

}
