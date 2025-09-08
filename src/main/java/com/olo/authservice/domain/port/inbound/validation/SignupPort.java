package com.olo.authservice.domain.port.inbound.validation;

import com.olo.authservice.domain.command.SignupCommand;
import com.olo.authservice.domain.result.AuthResult;

public interface SignupPort {
    AuthResult signup(SignupCommand command, String token);
}
