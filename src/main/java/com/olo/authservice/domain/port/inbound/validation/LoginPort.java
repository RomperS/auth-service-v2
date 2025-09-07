package com.olo.authservice.domain.port.inbound.validation;

import com.olo.authservice.domain.command.LoginCommand;
import com.olo.authservice.domain.model.User;

public interface LoginPort {
    User login(LoginCommand command);
}
