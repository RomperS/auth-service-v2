package com.olo.authservice.application.usecase.validation;

import com.olo.authservice.anotations.CustomTransactional;
import com.olo.authservice.application.service.TokenService;
import com.olo.authservice.domain.exception.user.UserNotFoundException;
import com.olo.authservice.domain.model.Token;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.inbound.validation.LogoutPort;
import com.olo.authservice.domain.port.outbound.JwtServicePort;
import com.olo.authservice.domain.port.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LogoutImpl implements LogoutPort {

    private final TokenService tokenService;
    private final UserRepositoryPort userRepositoryPort;
    private final JwtServicePort jwtServicePort;

    @Override
    @CustomTransactional
    public void logout(String token) {
        String username = jwtServicePort.getUsername(token);


        User user = userRepositoryPort.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Token> oldTokens = tokenService.getActiveUserToken(user.username());

        for (Token oldToken : oldTokens) {
            tokenService.revokeToken(oldToken.jti());
        }
    }
}
