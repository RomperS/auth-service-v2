package com.olo.authservice.domain.port.outbound;

import com.olo.authservice.domain.command.PublishCredentialsCommand;

public interface KafkaServicePort {
    void publishCredentialsCreated(PublishCredentialsCommand command);
    void publishUserCreated(String userId, String registerId);
}
