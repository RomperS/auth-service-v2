package com.olo.authservice.application.usecase.validation;

import com.olo.authservice.application.service.UserService;

import com.olo.authservice.domain.command.LoginCommand;
import com.olo.authservice.domain.command.SignupCommand;
import com.olo.authservice.domain.command.UpdateUserCommand;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.inbound.validation.LoginPort;
import com.olo.authservice.domain.port.inbound.validation.SignupPort;
import com.olo.authservice.domain.result.AuthResult;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SignupImpl implements SignupPort {

    private final UserService userService;
    private final LoginPort loginPort;


    @Override
    public AuthResult signup(SignupCommand command) {
        UpdateUserCommand updateCommand = new UpdateUserCommand(
                command.userId(),
                command.username(),
                command.email(),
                command.password()
        );

        User user = userService.updateUser(updateCommand);

        LoginCommand loginCommand = new LoginCommand(
                user.username(),
                command.password()
        );

        return loginPort.login(loginCommand);
    }
}
