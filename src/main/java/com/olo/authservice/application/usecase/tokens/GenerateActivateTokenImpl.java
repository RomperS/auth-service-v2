package com.olo.authservice.application.usecase.tokens;

import com.olo.authservice.domain.exception.user.UserNotFoundException;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.inbound.tokens.GenerateActivationTokenPort;
import com.olo.authservice.domain.port.outbound.JwtServicePort;
import com.olo.authservice.domain.port.outbound.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GenerateActivateTokenImpl implements GenerateActivationTokenPort {

    private final UserRepositoryPort userRepositoryPort;
    private final JwtServicePort jwtServicePort;

    @Override
    public String activationTokenGenerate(Long userId) {
        User user = userRepositoryPort.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        return jwtServicePort.generateActivateToken(user.username());
    }
}
