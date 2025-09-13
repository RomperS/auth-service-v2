package com.olo.authservice.domain.port.inbound.users;

import com.olo.authservice.domain.command.CreateSecretaryCommand;
import com.olo.authservice.domain.model.User;

public interface CreateSecretaryPort {
    User createSecretary(CreateSecretaryCommand command);
}
