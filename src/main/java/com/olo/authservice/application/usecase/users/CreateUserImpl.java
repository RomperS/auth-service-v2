package com.olo.authservice.application.usecase.users;

import com.olo.authservice.anotations.CustomTransactional;
import com.olo.authservice.application.service.TokenService;
import com.olo.authservice.domain.command.CreateUserCommand;
import com.olo.authservice.domain.command.EmailCommand;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.inbound.users.CreateUserPort;
import com.olo.authservice.domain.port.outbound.EmailServicePort;
import com.olo.authservice.domain.port.outbound.PasswordServicePort;
import com.olo.authservice.domain.port.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class CreateUserImpl implements CreateUserPort {

    private final UserRepositoryPort userRepositoryPort;
    private final EmailServicePort emailServicePort;
    private final TokenService tokenService;
    private final PasswordServicePort passwordServicePort;

    private final String baseUrl;

    @Override
    @CustomTransactional
    public User createUser(CreateUserCommand command) {
        CreateUserCommand createUserCommand = CreateUserCommand.of(command);

        String username = UUID.randomUUID().toString().substring(0, 8);
        String password = UUID.randomUUID().toString().substring(0, 16);

        User user = new User(
                null,
                username,
                createUserCommand.dni(),
                passwordServicePort.encode(password),
                false,
                List.of(createUserCommand.role()),
                List.of(createUserCommand.title())
        );

        User savedUser = userRepositoryPort.save(user);

        String activateToken = tokenService.generateActivationToken(savedUser.id());

        sendMail(activateToken, command.email(), username, password);

        return new User(
                savedUser.id(),
                savedUser.username(),
                savedUser.dni(),
                null,
                savedUser.accountLocked(),
                savedUser.roles(),
                savedUser.titles()
        );
    }

    public void sendMail(String activationToken, String email, String username, String password) {
        String finalLink = baseUrl + "?token=" + activationToken;

        String subject = "Account Activation & Temporary Credentials";
        String body = "Hello, your account has been created successfully. "
                + "\nYour temporary credentials are:"
                + "\n - Username: " + username
                + "\n - Password: " + password
                + "\n\nPlease click the following link to finish your registration and change your password: <a href='" + finalLink + "'>COMPLETE REGISTRATION</a>";

        EmailCommand emailCommand = new EmailCommand(
                email,
                subject,
                body
        );

        emailServicePort.sendEmail(emailCommand);
    }
}
