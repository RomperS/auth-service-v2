package com.olo.authservice.application.usecase.validation;

import com.olo.authservice.application.service.TokenService;
import com.olo.authservice.application.service.UserService;
import com.olo.authservice.domain.command.PublishCredentialsCommand;
import com.olo.authservice.domain.command.SignupCommand;
import com.olo.authservice.domain.command.UpdateUserCommand;
import com.olo.authservice.domain.exception.token.InvalidTokenException;
import com.olo.authservice.domain.exception.user.MalformedRecordException;
import com.olo.authservice.domain.model.Token;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.inbound.validation.SignupPort;
import com.olo.authservice.domain.port.outbound.JwtServicePort;
import com.olo.authservice.domain.port.outbound.KafkaServicePort;
import com.olo.authservice.domain.result.AccessTokenResult;
import com.olo.authservice.domain.result.AuthResult;
import com.olo.permissions.Role;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class SignupImpl implements SignupPort {

    private final UserService userService;
    private final TokenService tokenService;
    private final JwtServicePort  jwtServicePort;
    private final KafkaServicePort kafkaServicePort;

    @Override
    public AuthResult signup(SignupCommand command, String token) {
        String type = jwtServicePort.getTokenType(token);
        if (!type.equals("activate") || !jwtServicePort.validateToken(token)) {
            throw new InvalidTokenException("Invalid token");
        }

        Long userId = jwtServicePort.getUserId(token);

        UpdateUserCommand updateCommand = new UpdateUserCommand(
                userId,
                command.username(),
                command.email(),
                command.password()
        );

        User updatedUser = userService.updateUser(updateCommand);

        Token refreshToken = tokenService.createToken(updatedUser.username());

        String accessToken = tokenService.generateAccessToken(refreshToken.refreshToken());
        Instant expireAt = Instant.now().plusMillis(jwtServicePort.getAccessTokenExpiration());

        List<Role> roles = updatedUser.roles().stream().filter(c -> c != com.olo.permissions.Role.AUXILIARY_ADMIN).toList();
        if (roles.size() != 1){
            throw new MalformedRecordException("Invalid roles");
        }

        PublishCredentialsCommand publishCredentialsCommand = new PublishCredentialsCommand(
                userId.toString(),
                roles.get(0).toString()
        );

        registerCredentials(publishCredentialsCommand);

        return new AuthResult(
                refreshToken.refreshToken(),
                refreshToken.expiredAt(),
                new AccessTokenResult(
                        accessToken,
                        expireAt
                )
        );
    }

    private void registerCredentials(PublishCredentialsCommand command) {
        kafkaServicePort.publishCredentialsCreated(command);
    }
}
