package com.olo.authservice.application.usecase.permissions;

import com.olo.authservice.domain.command.TitleCommand;
import com.olo.authservice.domain.exception.user.UserNotFoundException;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.inbound.permissions.RemoveTitlePort;
import com.olo.authservice.domain.port.outbound.UserRepositoryPort;
import com.olo.permissions.Title;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RemoveTitleImpl  implements RemoveTitlePort {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void removeTitle(TitleCommand command) {
        command.verifyTitle();
        User user = userRepositoryPort.findById(command.userId()).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.titles().contains(command.title())) {
            List<Title> titles = user.titles().stream().filter(t -> t != command.title()).toList();
            User updatedUser = new User(
                    user.id(),
                    user.username(),
                    user.dni(),
                    user.password(),
                    user.accountLocked(),
                    user.roles(),
                    user.titles()
            );
            userRepositoryPort.save(updatedUser);
        }
    }
}