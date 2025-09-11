package com.olo.authservice.application.service;

import com.olo.authservice.application.usecase.validation.*;
import com.olo.authservice.domain.command.AuthCommand;
import com.olo.authservice.domain.command.AuthCommand;
import com.olo.authservice.domain.port.inbound.validation.*;
import com.olo.authservice.domain.result.AuthResult;
import com.olo.authservice.domain.result.ValidateTokenResult;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidationService implements LoginPort, LogoutPort, SignupPort, ValidateTokenPort {

    private final LoginImpl loginImpl;
    private final LogoutImpl logoutImpl;
    private final SignupImpl signupImpl;
    private final ValidateTokenImpl validateTokenImpl;

    @Override
    public AuthResult login(AuthCommand command) {
        return loginImpl.login(command);
    }

    @Override
    public void logout(String token) {
        logoutImpl.logout(token);
    }

    @Override
    public AuthResult signup(AuthCommand command, String token) {
        return  signupImpl.signup(command, token);
    }

    @Override
    public ValidateTokenResult validateToken(String token) {
        return  validateTokenImpl.validateToken(token);
    }
}
