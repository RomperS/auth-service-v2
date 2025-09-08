package com.olo.authservice.application.service;

import com.olo.authservice.application.usecase.users.*;
import com.olo.authservice.domain.command.CreateUserCommand;
import com.olo.authservice.domain.command.UpdateUserCommand;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.inbound.users.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService implements CreateUserPort, LockUserPort, UnlockUserPort, UpdateUserPort {

    private final CreateUserImpl createUserImpl;
    private final LockUserImpl lockUserImpl;
    private final UnlockUserImpl unlockUserImpl;
    private final UpdateUserImpl updateUserImpl;

    @Override
    public User createUser(CreateUserCommand command) {
        return createUserImpl.createUser(command);
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
}