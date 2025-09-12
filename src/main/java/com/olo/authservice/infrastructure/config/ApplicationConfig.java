package com.olo.authservice.infrastructure.config;

import com.olo.authservice.application.service.PermissionService;
import com.olo.authservice.application.service.TokenService;
import com.olo.authservice.application.service.UserService;
import com.olo.authservice.application.service.ValidationService;
import com.olo.authservice.application.usecase.permissions.AssignBoardRoleImpl;
import com.olo.authservice.application.usecase.permissions.RevokeBoardRoleImpl;
import com.olo.authservice.application.usecase.tokens.*;
import com.olo.authservice.application.usecase.users.*;
import com.olo.authservice.application.usecase.validation.LoginImpl;
import com.olo.authservice.application.usecase.validation.LogoutImpl;
import com.olo.authservice.application.usecase.validation.SignupImpl;
import com.olo.authservice.application.usecase.validation.ValidateTokenImpl;
import com.olo.authservice.domain.port.outbound.*;
import com.olo.authservice.infrastructure.adapters.EmailServiceAdapter;
import com.olo.authservice.infrastructure.adapters.kafka.KafkaProducerAdapter;
import com.olo.authservice.infrastructure.adapters.repository.TokenRepositoryAdapter;
import com.olo.authservice.infrastructure.adapters.repository.UserRepositoryAdapter;
import com.olo.authservice.infrastructure.repositories.JpaTokenRepository;
import com.olo.authservice.infrastructure.security.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class ApplicationConfig {

    @Bean
    public UserRepositoryPort userRepositoryPort(UserRepositoryAdapter userRepositoryAdapter) {
        return userRepositoryAdapter;
    }

    @Bean
    public TokenRepositoryPort tokenRepositoryPort(TokenRepositoryAdapter tokenRepositoryAdapter) {
        return tokenRepositoryAdapter;
    }

    @Bean
    public KafkaProducerPort kafkaProducerPort(KafkaProducerAdapter kafkaProducerAdapter) {
        return kafkaProducerAdapter;
    }

    @Bean
    public EmailServicePort emailServicePort(EmailServiceAdapter emailServiceAdapter) {
        return emailServiceAdapter;
    }

    @Bean
    public UserService userService(UserRepositoryPort userRepositoryPort,
                                   PasswordServicePort passwordServicePort,
                                   EmailServicePort emailServicePort,
                                   TokenService tokenService,
                                   @Value("${base-url.signup}") String baseUrl) {

        return new UserService(
                new CreateUserImpl(userRepositoryPort, emailServicePort, tokenService, passwordServicePort, baseUrl),
                new GetUserByDniImpl(userRepositoryPort),
                new LockUserImpl(userRepositoryPort),
                new UnlockUserImpl(userRepositoryPort),
                new UpdateUserImpl(userRepositoryPort, passwordServicePort)
        );
    }

    @Bean
    public TokenService tokenService(TokenRepositoryPort tokenRepositoryPort, JwtServicePort jwtServicePort, UserRepositoryPort userRepositoryPort) {
        return new TokenService(
                new CreateTokenImpl(tokenRepositoryPort, jwtServicePort),
                new GenerateAccessTokenImpl(tokenRepositoryPort, jwtServicePort),
                new GenerateActivateTokenImpl(userRepositoryPort, jwtServicePort),
                new GetActiveUserTokensImpl(tokenRepositoryPort),
                new RevokeTokenImpl(tokenRepositoryPort)
        );
    }

    @Bean
    public PermissionService permissionService(UserRepositoryPort userRepositoryPort) {
        return new PermissionService(
                new AssignBoardRoleImpl(userRepositoryPort),
                new RevokeBoardRoleImpl(userRepositoryPort)
        );
    }

    @Bean
    public ValidationService validationService(PasswordServicePort passwordServicePort,
                                               UserRepositoryPort userRepositoryPort,
                                               TokenService tokenService,
                                               UserService userService,
                                               JwtServicePort jwtServicePort,
                                               KafkaProducerPort kafkaProducerPort) {
        return new ValidationService(
                new LoginImpl(userRepositoryPort, tokenService, passwordServicePort, jwtServicePort),
                new LogoutImpl(tokenService, userRepositoryPort, jwtServicePort),
                new SignupImpl(userService, tokenService, jwtServicePort, kafkaProducerPort),
                new ValidateTokenImpl(jwtServicePort)
        );
    }

    @Bean
    public JwtServicePort jwtServicePort(JpaTokenRepository jpaTokenRepository, UserDetailsService userDetailsService) {
        return new JwtServiceImpl(jpaTokenRepository, userDetailsService);
    }
}
