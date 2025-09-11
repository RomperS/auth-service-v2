package com.olo.authservice.domain.port.inbound.validation;

import com.olo.authservice.domain.command.AuthCommand;
import com.olo.authservice.domain.result.AuthResult;

public interface SignupPort {
    AuthResult signup(AuthCommand command, String token);
}
