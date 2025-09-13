package com.olo.authservice.application.service;

import com.olo.authservice.application.usecase.users.*;
import com.olo.authservice.domain.command.CreateSecretaryCommand;
import com.olo.authservice.domain.command.CreateUserCommand;
import com.olo.authservice.domain.command.UpdateUserCommand;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.inbound.users.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService implements CreateUserPort, CreateSecretaryPort, GetUserByDniPort, LockUserPort, UnlockUserPort, UpdateUserPort {

    private final CreateUserImpl createUserImpl;
    private final CreateSecretaryImpl createSecretaryImpl;
    private final GetUserByDniImpl getUserByDniImpl;
    private final LockUserImpl lockUserImpl;
    private final UnlockUserImpl unlockUserImpl;
    private final UpdateUserImpl updateUserImpl;

    @Override
    public User createUser(CreateUserCommand command) {
        return createUserImpl.createUser(command);
    }

    @Override
    public User getUserByDni(Long dni) {
        return getUserByDniImpl.getUserByDni(dni);
    }

    @Override
    public void lockUser(Long userId) {
        lockUserImpl.lockUser(userId);
    }

    @Override
    public void unlockUser(Long userId) {
        unlockUserImpl.unlockUser(userId);
    }

    @Override
    public User updateUser(UpdateUserCommand command) {
        return updateUserImpl.updateUser(command);
    }

    @Override
    public User createSecretary(CreateSecretaryCommand command) {
        return createSecretaryImpl.createSecretary(command);
    }
}