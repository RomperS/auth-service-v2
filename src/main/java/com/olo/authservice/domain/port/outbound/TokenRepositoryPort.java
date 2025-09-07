package com.olo.authservice.domain.port.outbound;

import com.olo.authservice.domain.model.Token;

import java.util.List;
import java.util.Optional;

public interface TokenRepositoryPort {

    Token save(Token token);

    Optional<Token> findByJti(String jti);
    List<Token> findAllByUsername(String username);
}
