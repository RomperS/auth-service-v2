package com.olo.authservice.infrastructure.config;

import com.olo.authservice.application.service.PermissionService;
import com.olo.authservice.application.service.TokenService;
import com.olo.authservice.application.service.UserService;
import com.olo.authservice.application.service.ValidationService;
import com.olo.authservice.application.usecase.permissions.AssignBoardRoleImpl;
import com.olo.authservice.application.usecase.permissions.RevokeBoardRoleImpl;
import com.olo.authservice.application.usecase.tokens.CreateTokenImpl;
import com.olo.authservice.application.usecase.tokens.GenerateAccessTokenImpl;
import com.olo.authservice.application.usecase.tokens.GetActiveUserTokensImpl;
import com.olo.authservice.application.usecase.tokens.RevokeTokenImpl;
import com.olo.authservice.application.usecase.users.CreateUserImpl;
import com.olo.authservice.application.usecase.users.LockUserImpl;
import com.olo.authservice.application.usecase.users.UnlockUserImpl;
import com.olo.authservice.application.usecase.users.UpdateUserImpl;
import com.olo.authservice.application.usecase.validation.LoginImpl;
import com.olo.authservice.application.usecase.validation.LogoutImpl;
import com.olo.authservice.application.usecase.validation.SignupImpl;
import com.olo.authservice.application.usecase.validation.ValidateTokenImpl;
import com.olo.authservice.domain.port.inbound.validation.LoginPort;
import com.olo.authservice.domain.port.outbound.*;
import com.olo.authservice.infrastructure.adapters.TokenRepositoryAdapter;
import com.olo.authservice.infrastructure.adapters.UserRepositoryAdapter;
import com.olo.authservice.infrastructure.repositories.JpaTokenRepository;
import com.olo.authservice.infrastructure.security.JwtServiceImpl;
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
    public UserService userService(UserRepositoryPort userRepositoryPort, PasswordServicePort passwordServicePort, CommonStringPort commonStringPort) {
        return new UserService(
                new CreateUserImpl(userRepositoryPort, commonStringPort),
                new LockUserImpl(userRepositoryPort),
                new UnlockUserImpl(userRepositoryPort),
                new UpdateUserImpl(userRepositoryPort, passwordServicePort)
        );
    }

    @Bean
    public TokenService tokenService(TokenRepositoryPort tokenRepositoryPort, JwtServicePort jwtServicePort) {
        return new TokenService(
                new CreateTokenImpl(tokenRepositoryPort, jwtServicePort),
                new GenerateAccessTokenImpl(tokenRepositoryPort, jwtServicePort),
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
                                               JwtServicePort jwtServicePort) {
        return new ValidationService(
                new LoginImpl(userRepositoryPort, tokenService, passwordServicePort, jwtServicePort),
                new LogoutImpl(tokenService, userRepositoryPort, jwtServicePort),
                new SignupImpl(userService, tokenService, jwtServicePort),
                new ValidateTokenImpl(jwtServicePort)
        );
    }

    @Bean
    public JwtServicePort jwtServicePort(JpaTokenRepository jpaTokenRepository, UserDetailsService userDetailsService) {
        return new JwtServiceImpl(jpaTokenRepository, userDetailsService);
    }
}
