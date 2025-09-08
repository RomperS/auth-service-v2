package com.olo.authservice.application.usecase.validation;

import com.olo.authservice.application.service.TokenService;
import com.olo.authservice.domain.command.LoginCommand;
import com.olo.authservice.domain.exception.user.InvalidCredentialsException;
import com.olo.authservice.domain.exception.user.UserNotFoundException;
import com.olo.authservice.domain.model.Token;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.inbound.validation.LoginPort;
import com.olo.authservice.domain.port.outbound.JwtServicePort;
import com.olo.authservice.domain.port.outbound.PasswordServicePort;
import com.olo.authservice.domain.port.outbound.UserRepositoryPort;
import com.olo.authservice.domain.result.AccessTokenResult;
import com.olo.authservice.domain.result.AuthResult;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class LoginImpl implements LoginPort {

    private final UserRepositoryPort userRepositoryPort;
    private final TokenService tokenService;
    private final PasswordServicePort passwordServicePort;
    private final JwtServicePort jwtServicePort;

    @Override
    public AuthResult login(LoginCommand command) {
        User user = userRepositoryPort.findByUsername(command.username()).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordServicePort.matches(command.password(), user.password())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        List<Token> tokens = tokenService.getActiveUserToken(user.username());

        for (Token token : tokens) {
            tokenService.revokeToken(token.jti());
        }

        Token token = tokenService.createToken(user.username());

        String accessToken = tokenService.generateAccessToken(token.refreshToken());
        Instant expireAt = Instant.now().plusMillis(jwtServicePort.getAccessTokenExpiration());

        return new AuthResult(
                token.refreshToken(),
                token.expiredAt(),
                new AccessTokenResult(
                        accessToken,
                        expireAt
                )
        );
    }
}
