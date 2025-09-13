package com.olo.authservice.infrastructure.adapters.kafka;

import com.olo.authservice.application.service.UserService;
import com.olo.authservice.domain.command.CreateUserCommand;
import com.olo.authservice.domain.exception.DomainException;
import com.olo.authservice.domain.exception.kafka.InvalidMessageFormatException;
import com.olo.authservice.domain.port.outbound.KafkaProducerPort;
import com.olo.internalauthlibrary.permissions.*;
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
    private final KafkaProducerPort kafkaProducerPort;

    @KafkaListener(topics = "created-user", groupId = "auth-service-group")
    public void createCredentials(String msg){
        String dni = "";
        try{
            String regex = "User with email '(.+?)', role '(.+?)', title '(.+?)' and dni '(.+?)' created";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(msg);
            if (matcher.find()) {
                String email = matcher.group(1);
                String role = matcher.group(2);
                String title = matcher.group(3);
                dni = matcher.group(4);
                Long longDni = Long.parseLong(dni);

                CreateUserCommand command = new CreateUserCommand(
                        email,
                        longDni,
                        Role.fromString(role),
                        Title.fromString(title)
                );
                userService.createUser(command);
            }else {
                throw new InvalidMessageFormatException("The message does not match the expected format.");
            }
        } catch (DomainException e) {
            log.error("An expected domain exception occurred: {}", e.getMessage(), e);
            createCredentialsCompensationEvent(e.getMessage(), dni);
            throw e;
        } catch (Exception e) {
            log.error("An unexpected error occurred during message processing.", e);
            createCredentialsCompensationEvent(e.getMessage(), dni);
            throw new RuntimeException("Unexpected error during user creation.", e);
        }
    }

    private void createCredentialsCompensationEvent(String exceptionMsg, String dni) {
        Long longDni = Long.parseLong(dni);
        kafkaProducerPort.createCompensationEvent(exceptionMsg, longDni);
    }
}
