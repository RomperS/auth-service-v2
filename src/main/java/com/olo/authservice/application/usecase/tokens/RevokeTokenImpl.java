package com.olo.authservice.application.usecase.tokens;

import com.olo.authservice.anotations.CustomTransactional;
import com.olo.authservice.domain.exception.token.TokenNotFoundException;
import com.olo.authservice.domain.model.Token;
import com.olo.authservice.domain.port.inbound.tokens.RevokeTokenPort;
import com.olo.authservice.domain.port.outbound.TokenRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RevokeTokenImpl implements RevokeTokenPort {

    private final TokenRepositoryPort tokenRepositoryPort;

    @Override
    @CustomTransactional
    public void revokeToken(String jti) {
        Token token = tokenRepositoryPort.findByJti(jti).orElseThrow(() -> new TokenNotFoundException("Token not found by jti"));

        Token revokedToken = new Token(
                token.id(),
                token.jti(),
                token.userId(),
                token.refreshToken(),
                true,
                token.expiredAt()
        );

        tokenRepositoryPort.save(revokedToken);
    }
}
