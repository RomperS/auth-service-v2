package com.olo.authservice.application.usecase.users;

import com.olo.authservice.anotations.CustomTransactional;
import com.olo.authservice.domain.exception.user.UserNotFoundException;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.inbound.users.UnlockUserPort;
import com.olo.authservice.domain.port.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UnlockUserImpl implements UnlockUserPort {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    @CustomTransactional
    public void unlockUser(Long userId) {
        User user = userRepositoryPort.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.accountLocked()){
            User unlockedUser = new User(
                    user.id(),
                    user.username(),
                    user.dni(),
                    user.password(),
                    false,
                    user.roles(),
                    user.titles()
            );

            userRepositoryPort.save(unlockedUser);
        }
    }
}
