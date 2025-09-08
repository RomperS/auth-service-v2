package com.olo.authservice.domain.port.outbound;

import com.olo.authservice.domain.command.EmailCommand;

public interface EmailServicePort {
    void sendEmail(EmailCommand emailCommand);
}
