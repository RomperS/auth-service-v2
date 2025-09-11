package com.olo.authservice.domain.port.outbound;

public interface KafkaProducerPort {
    void createCompensationEvent(String exception, Long dni);
}
