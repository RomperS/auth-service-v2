package com.olo.authservice.application.usecase.tokens;

import com.olo.authservice.anotations.CustomTransactional;
import com.olo.authservice.domain.model.Token;
import com.olo.authservice.domain.port.inbound.tokens.CreateTokenPort;
import com.olo.authservice.domain.port.outbound.JwtServicePort;
import com.olo.authservice.domain.port.outbound.TokenRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class CreateTokenImpl implements CreateTokenPort {

    private final TokenRepositoryPort tokenRepositoryPort;
    private final JwtServicePort jwtServicePort;

    @Override
    @CustomTransactional
    public Token createToken(String username) {
        String token = jwtServicePort.generateRefreshToken(username);
        Instant expireAt = Instant.now().plusMillis(jwtServicePort.getRefreshTokenExpiration());
        String jti = jwtServicePort.getJti(token);
        Long userId = jwtServicePort.getUserId(token);

        Token tokenModel = new Token(
                null,
                jti,
                userId,
                token,
                false,
                expireAt
        );

        return tokenRepositoryPort.save(tokenModel);
    }
}
