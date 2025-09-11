package com.olo.authservice.application.usecase.permissions;

import com.olo.authservice.domain.exception.user.UserNotFoundException;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.inbound.permissions.AssignBoardRolePort;
import com.olo.authservice.domain.port.outbound.UserRepositoryPort;
import com.olo.permissions.Role;
import com.olo.permissions.Title;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class AssignBoardRoleImpl implements AssignBoardRolePort {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void assignBoardRole(Long userId) {
        User user = userRepositoryPort.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        Set<Role> roles = new HashSet<>(user.roles());
        Set<Title> titles = new HashSet<>(user.titles());

        roles.add(Role.AUXILIARY_ADMIN);
        titles.add(Title.DIRECTORS_BOARD);

        User updatedUser = new User(
                user.id(),
                user.username(),
                user.dni(),
                user.password(),
                user.accountLocked(),
                new ArrayList<>(roles),
                new ArrayList<>(titles)
        );

        userRepositoryPort.save(updatedUser);
    }
}
