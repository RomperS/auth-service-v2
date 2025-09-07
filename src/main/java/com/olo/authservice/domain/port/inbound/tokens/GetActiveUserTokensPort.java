package com.olo.authservice.domain.port.inbound.tokens;

import com.olo.authservice.domain.model.Token;

import java.util.List;

public interface GetActiveUserTokensPort {
    List<Token> getActiveUserToken(String username);
}
