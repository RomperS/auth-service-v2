package com.olo.authservice.application.usecase.users;

import com.olo.authservice.domain.exception.user.UserNotFoundException;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.inbound.users.GetUserByDniPort;
import com.olo.authservice.domain.port.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetUserByDniImpl implements GetUserByDniPort {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public User getUserByDni(Long dni) {
        return userRepositoryPort.findByDni(dni).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
