package com.olo.authservice.domain.port.inbound.users;

import com.olo.authservice.domain.command.UpdateUserCommand;
import com.olo.authservice.domain.model.User;

public interface UpdateUserPort {
    User updateUser(UpdateUserCommand command);
}
