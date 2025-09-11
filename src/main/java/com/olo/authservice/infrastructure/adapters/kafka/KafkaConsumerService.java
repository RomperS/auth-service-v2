package com.olo.authservice.infrastructure.adapters.kafka;

import com.olo.authservice.application.service.UserService;
import com.olo.authservice.domain.command.CreateUserCommand;
import com.olo.authservice.domain.model.User;
import com.olo.permissions.Role;
import com.olo.permissions.Title;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final UserService userService;

    @KafkaListener(topics = "created-user", groupId = "auth-service-group")
    public void createCredentials(String msg){
        String regex = "User with email '(.+?)', role '(.+?)' and title '(.+?)' created\\.";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(msg);

        if (matcher.find()) {
            String email = matcher.group(1);
            String role = matcher.group(2);
            String title = matcher.group(3);
            CreateUserCommand command = new CreateUserCommand(
                    email,
                    Role.fromString(role),
                    Title.fromString(title)
            );
            User createdUser = userService.createUser(command);

            log.info("Create credentials: {}", createdUser);
        }
    }
}
