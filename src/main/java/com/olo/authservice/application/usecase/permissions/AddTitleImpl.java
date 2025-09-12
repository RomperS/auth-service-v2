package com.olo.authservice.application.usecase.permissions;

import com.olo.authservice.domain.command.TitleCommand;
import com.olo.authservice.domain.exception.user.UserNotFoundException;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.inbound.permissions.AddTitlePort;
import com.olo.authservice.domain.port.outbound.UserRepositoryPort;
import com.olo.exceptions.permissions.InvalidPermissionValueException;
import com.olo.permissions.Title;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AddTitleImpl  implements AddTitlePort {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void addTitle(TitleCommand command) {
        command.verifyTitle();
        User user = userRepositoryPort.findById(command.userId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Title> titles = user.titles();

        if (!titles.contains(command.title())) {
            if (!user.roles().contains(command.title().getRole())) {
                throw new InvalidPermissionValueException("Invalid title for this user");
            }
            titles.add(command.title());
            User updatedUser = new User(
                    user.id(),
                    user.username(),
                    user.dni(),
                    user.password(),
                    user.accountLocked(),
                    user.roles(),
                    titles
            );
            userRepositoryPort.save(updatedUser);
        }
    }
}
