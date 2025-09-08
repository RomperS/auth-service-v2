package com.olo.authservice.application.usecase.tokens;

import com.olo.authservice.domain.exception.token.InvalidTokenException;
import com.olo.authservice.domain.exception.token.TokenNotFoundException;
import com.olo.authservice.domain.exception.token.TokenRevokedException;
import com.olo.authservice.domain.model.Token;
import com.olo.authservice.domain.port.inbound.tokens.GenerateAccessTokenPort;
import com.olo.authservice.domain.port.outbound.JwtServicePort;
import com.olo.authservice.domain.port.outbound.TokenRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GenerateAccessTokenImpl implements GenerateAccessTokenPort {

    private final TokenRepositoryPort tokenRepositoryPort;
    private final JwtServicePort jwtServicePort;

    @Override
    public String generateAccessToken(String token) {
        String jti = jwtServicePort.getJti(token);

        Token refreshToken = tokenRepositoryPort.findByJti(jti).orElseThrow(() -> new TokenNotFoundException("Token not found"));

        if (refreshToken.isRevoked()) {
            throw new TokenRevokedException("Token is revoked");
        }

        if (!jwtServicePort.validateToken(refreshToken.refreshToken())) {
            throw new InvalidTokenException("Token is not valid");
        }

        String username = jwtServicePort.getUsername(refreshToken.refreshToken());

        return jwtServicePort.generateAccessToken(username);
    }
}
