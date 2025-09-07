package com.olo.authservice.domain.port.inbound.tokens;

import com.olo.authservice.domain.model.Token;

public interface CreateTokenPort {
    Token createToken(String username);
}
