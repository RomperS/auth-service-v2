package com.olo.authservice.application.usecase.tokens;

import com.olo.authservice.domain.model.Token;
import com.olo.authservice.domain.port.inbound.tokens.GetActiveUserTokensPort;
import com.olo.authservice.domain.port.outbound.TokenRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class GetActiveUserTokensImpl implements GetActiveUserTokensPort {

    private final TokenRepositoryPort tokenRepositoryPort;

    @Override
    public List<Token> getActiveUserToken(String username) {
        List<Token> tokens = tokenRepositoryPort.findAllByUsername(username);

        return tokens.stream()
                .filter(token -> !token.isRevoked() && !(Instant.now().isAfter(token.expiredAt())))
                .toList();
    }
}
