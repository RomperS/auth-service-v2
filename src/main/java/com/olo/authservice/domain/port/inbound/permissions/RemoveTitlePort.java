package com.olo.authservice.domain.port.inbound.permissions;

import com.olo.authservice.domain.command.TitleCommand;

public interface RemoveTitlePort {
    void removeTitle(TitleCommand command);
}
