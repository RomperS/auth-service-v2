package com.olo.authservice.application.usecase.users;

import com.olo.authservice.anotations.CustomTransactional;
import com.olo.authservice.domain.command.UpdateUserCommand;
import com.olo.authservice.domain.exception.user.EmailRegisteredException;
import com.olo.authservice.domain.exception.user.UserNotFoundException;
import com.olo.authservice.domain.exception.user.UsernameTakenException;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.inbound.users.UpdateUserPort;
import com.olo.authservice.domain.port.outbound.PasswordServicePort;
import com.olo.authservice.domain.port.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateUserImpl implements UpdateUserPort {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordServicePort passwordServicePort;

    @Override
    @CustomTransactional
    public User updateUser(UpdateUserCommand command) {
        User user = userRepositoryPort.findById(command.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String newUsername = command.username();
        if (newUsername != null && !newUsername.equals(user.username())) {
            if (userRepositoryPort.existByUsername(newUsername)) {
                throw new UsernameTakenException("Username is already taken");
            }
        } else {
            newUsername = user.username();
        }

        String newEmail = command.email();
        if (newEmail != null && !newEmail.equals(user.email())) {
            if (userRepositoryPort.existByEmail(newEmail)) {
                throw new EmailRegisteredException(
                        String.format("Email '%s' is already registered.", newEmail)
                );
            }
        } else {
            newEmail = user.email();
        }

        String newPassword = command.password();
        if (newPassword != null) {
            newPassword = passwordServicePort.encode(newPassword);
        } else {
            newPassword = user.password();
        }

        User updatedUser = new User(
                user.id(),
                newUsername,
                newEmail,
                newPassword,
                user.accountLocked(),
                user.roles(),
                user.titles()
        );

        return userRepositoryPort.save(updatedUser);
    }
}