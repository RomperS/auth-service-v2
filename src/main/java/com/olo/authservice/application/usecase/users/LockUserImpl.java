package com.olo.authservice.application.usecase.users;

import com.olo.authservice.anotations.CustomTransactional;
import com.olo.authservice.domain.exception.user.AdminUserBlockNotAllowedException;
import com.olo.authservice.domain.exception.user.UserNotFoundException;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.inbound.users.LockUserPort;
import com.olo.authservice.domain.port.outbound.UserRepositoryPort;
import com.olo.internalauthlibrary.permissions.Role;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LockUserImpl implements LockUserPort {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    @CustomTransactional
    public void lockUser(Long userId) {
        User user = userRepositoryPort.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.roles().contains(Role.SUPER_ADMIN)) {
            throw new AdminUserBlockNotAllowedException("Blocking an super-administrator is not permitted.");
        }

        if (!user.accountLocked()){
            User unlockedUser = new User(
                    user.id(),
                    user.username(),
                    user.dni(),
                    user.password(),
                    true,
                    user.roles(),
                    user.titles()
            );

            userRepositoryPort.save(unlockedUser);
        }
    }
}
