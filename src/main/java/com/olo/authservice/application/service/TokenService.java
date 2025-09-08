package com.olo.authservice.application.service;

import com.olo.authservice.application.usecase.tokens.CreateTokenImpl;
import com.olo.authservice.application.usecase.tokens.GenerateAccessTokenImpl;
import com.olo.authservice.application.usecase.tokens.GetActiveUserTokensImpl;
import com.olo.authservice.application.usecase.tokens.RevokeTokenImpl;
import com.olo.authservice.application.usecase.tokens.*;
import com.olo.authservice.domain.model.Token;
import com.olo.authservice.domain.port.inbound.tokens.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TokenService implements CreateTokenPort, GenerateAccessTokenPort, GenerateActivationTokenPort, GetActiveUserTokensPort, RevokeTokenPort {

    private final CreateTokenImpl createTokenImpl;
    private final GenerateAccessTokenImpl generateAccessTokenImpl;
    private final GenerateActivateTokenImpl  generateActivateTokenImpl;
    private final GetActiveUserTokensImpl getActiveUserTokensImpl;
    private final RevokeTokenImpl revokeTokenImpl;

    @Override
    public Token createToken(String username) {
        return createTokenImpl.createToken(username);
    }

    @Override
    public String generateAccessToken(String token) {
        return generateAccessTokenImpl.generateAccessToken(token);
    }

    @Override
    public String generateActivationToken(Long userId) {
        return generateActivateTokenImpl.generateActivationToken(userId);
    }


    @Override
    public List<Token> getActiveUserToken(String username) {
        return  getActiveUserTokensImpl.getActiveUserToken(username);
    }

    @Override
    public void revokeToken(String jti) {
        revokeTokenImpl.revokeToken(jti);
    }
}
