package com.olo.authservice.infrastructure.config;

import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.outbound.PasswordServicePort;
import com.olo.authservice.domain.port.outbound.UserRepositoryPort;
import com.olo.permissions.Role;
import com.olo.permissions.Title;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepositoryPort userRepository;
    private final PasswordServicePort passwordServicePort;
    @Value("${security.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (userRepository.findSuperAdmin().isEmpty()) {
            List<Role> roles = List.of(Role.SUPER_ADMIN, Role.AUXILIARY_ADMIN);
            List<Title> titles = List.of(Title.PRINCIPAL , Title.DIRECTORS_BOARD);

            User adminUser = new User(
                    null,
                    "Admin",
                    1002492530L,
                    passwordServicePort.encode(adminPassword),
                    false,
                    roles,
                    titles
            );

            userRepository.save(adminUser);
            System.out.println("Admin user created.");
        }
    }
}