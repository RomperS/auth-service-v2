package com.olo.authservice.infrastructure.adapters.kafka;

import com.olo.authservice.domain.port.outbound.KafkaProducerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducerAdapter implements KafkaProducerPort {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void createCompensationEvent(String exception, Long dni) {
        String dniJson = (dni != null) ? String.valueOf(dni) : "null";
        String payload = String.format("{\"exceptionMessage\":\"%s\", \"dni\":%s}", exception, dniJson);
        kafkaTemplate.send("user-creation-compensation", payload);
    }
}
