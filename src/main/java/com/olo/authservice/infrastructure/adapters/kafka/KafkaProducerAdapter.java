package com.olo.authservice.infrastructure.adapters.kafka;

import com.olo.authservice.domain.command.PublishCredentialsCommand;
import com.olo.authservice.domain.port.outbound.KafkaProducerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducerAdapter implements KafkaProducerPort {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publishCredentialsCreated(PublishCredentialsCommand command) {
        String payload = String.format("{\"userId\":\"%s\",\"role\":\"%s\"}", command.userId(), command.role());
        kafkaTemplate.send("credentials-created", payload);
    }
}
