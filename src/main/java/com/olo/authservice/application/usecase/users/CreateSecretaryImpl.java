package com.olo.authservice.application.usecase.users;

import com.olo.authservice.domain.command.CreateSecretaryCommand;
import com.olo.authservice.domain.command.DniAlreadyExistException;
import com.olo.authservice.domain.exception.user.UsernameTakenException;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.inbound.users.CreateSecretaryPort;
import com.olo.authservice.domain.port.outbound.PasswordServicePort;
import com.olo.authservice.domain.port.outbound.UserRepositoryPort;
import com.olo.internalauthlibrary.permissions.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CreateSecretaryImpl implements CreateSecretaryPort {
    
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordServicePort passwordServicePort;


    @Override
    public User createSecretary(CreateSecretaryCommand command) {
        if (userRepositoryPort.existByUsername(command.username())) {
            throw new UsernameTakenException("Username taken");
        }
        if (userRepositoryPort.existByDni(command.dni())) {
            throw new DniAlreadyExistException("Dni already exist");
        }

        List<Role> roles = List.of(Role.ADMIN);
        List<Title> titles = List.of(Title.SECRETARY);
        User user = new User(
                null,
                command.username(),
                command.dni(),
                passwordServicePort.encode(command.password()),
                false,
                roles,
                titles
        );

        return userRepositoryPort.save(user);
    }
}
