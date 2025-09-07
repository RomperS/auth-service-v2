package com.olo.authservice.domain.port.inbound.validation;

import com.olo.authservice.domain.command.SignupCommand;
import com.olo.authservice.domain.model.User;

public interface SignupPort {
    User signup(SignupCommand command);
}
