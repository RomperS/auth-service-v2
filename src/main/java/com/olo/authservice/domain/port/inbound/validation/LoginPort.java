package com.olo.authservice.domain.port.inbound.validation;

import com.olo.authservice.domain.command.LoginCommand;
import com.olo.authservice.domain.result.AuthResult;

public interface LoginPort {
    AuthResult login(LoginCommand command);
}
