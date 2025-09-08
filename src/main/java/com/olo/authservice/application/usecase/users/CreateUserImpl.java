package com.olo.authservice.application.usecase.users;

import com.olo.authservice.anotations.CustomTransactional;
import com.olo.authservice.domain.command.CreateUserCommand;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.inbound.users.CreateUserPort;
import com.olo.authservice.domain.port.outbound.CommonStringPort;
import com.olo.authservice.domain.port.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CreateUserImpl implements CreateUserPort {

    private final UserRepositoryPort userRepositoryPort;
    private final CommonStringPort commonStringPort;

    @Override
    @CustomTransactional
    public User createUser(CreateUserCommand command) {
        CreateUserCommand createUserCommand = CreateUserCommand.of(command);

        String username = createUserCommand.role().toString() + commonStringPort.randomAlphanumeric(10);

        User user = new User(
                null,
                username,
                null,
                null,
                true,
                List.of(createUserCommand.role()),
                List.of(createUserCommand.title())
        );

        return userRepositoryPort.save(user);
    }
}
