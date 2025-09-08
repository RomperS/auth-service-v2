package com.olo.authservice.infrastructure.adapters;

import com.olo.authservice.domain.command.PublishCredentialsCommand;
import com.olo.authservice.domain.port.outbound.KafkaServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaServiceAdapter implements KafkaServicePort {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publishCredentialsCreated(PublishCredentialsCommand command) {
        String payload = String.format("{\"userId\":\"%s\",\"role\":\"%s\"}", command.userId(), command.role());
        kafkaTemplate.send("credentials-created", payload);
    }

    @Override
    public void publishUserCreated(String userId, String registerId) {

    }
}
