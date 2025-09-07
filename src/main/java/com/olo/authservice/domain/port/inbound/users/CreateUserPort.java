package com.olo.authservice.domain.port.inbound.users;

import com.olo.authservice.domain.command.CreateUserCommand;
import com.olo.authservice.domain.model.User;

public interface CreateUserPort {
    User createUser(CreateUserCommand command);
}
