package com.olo.authservice.application.usecase.permissions;

import com.olo.authservice.domain.exception.user.UserNotFoundException;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.inbound.permissions.RevokeBoardRolePort;
import com.olo.authservice.domain.port.outbound.UserRepositoryPort;
import com.olo.permissions.Role;
import com.olo.permissions.Title;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RevokeBoardRoleImpl implements RevokeBoardRolePort {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void revokeBoardRole(Long userId) {
        User user = userRepositoryPort.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Role> roles = user.roles().stream().filter(c -> c != Role.AUXILIARY_ADMIN).toList();
        List<Title> titles = user.titles().stream().filter(c -> c != Title.DIRECTORS_BOARD).toList();

        User updatedUser = new User(
                user.id(),
                user.username(),
                user.email(),
                user.password(),
                user.accountLocked(),
                roles,
                titles
        );

        userRepositoryPort.save(updatedUser);
    }
}
