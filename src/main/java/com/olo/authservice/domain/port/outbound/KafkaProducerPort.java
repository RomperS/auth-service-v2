package com.olo.authservice.domain.port.outbound;

import com.olo.authservice.domain.command.PublishCredentialsCommand;

public interface KafkaProducerPort {
    void publishCredentialsCreated(PublishCredentialsCommand command);
}
