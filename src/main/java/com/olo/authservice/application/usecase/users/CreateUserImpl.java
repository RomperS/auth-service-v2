package com.olo.authservice.application.usecase.users;

import com.olo.authservice.anotations.CustomTransactional;
import com.olo.authservice.application.service.TokenService;
import com.olo.authservice.domain.command.CreateUserCommand;
import com.olo.authservice.domain.command.EmailCommand;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.inbound.users.CreateUserPort;
import com.olo.authservice.domain.port.outbound.CommonStringPort;
import com.olo.authservice.domain.port.outbound.EmailServicePort;
import com.olo.authservice.domain.port.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CreateUserImpl implements CreateUserPort {

    private final UserRepositoryPort userRepositoryPort;
    private final CommonStringPort commonStringPort;
    private final EmailServicePort emailServicePort;
    private final TokenService tokenService;

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

        User savedUser = userRepositoryPort.save(user);

        String activateToken = tokenService.generateActivationToken(savedUser.id());

        sendMail(activateToken, command.email());

        return savedUser;
    }

    public void sendMail(String activationToken, String email) {
        String baseUrl = "http://127.0.0.1:5500/index.html?token=";

        String finalLink = baseUrl + "?token=" + activationToken;

        String subject = "Finalizar registro";
        String body = "Para terminar su registro, haga clic en el siguiente enlace: <a href='" + finalLink + "'>TERMINAR REGISTRO</a>";

        EmailCommand emailCommand = new EmailCommand(
                email,
                subject,
                body
        );

        emailServicePort.sendEmail(emailCommand);
    }
}
