package com.olo.authservice.domain.port.inbound.validation;

import com.olo.authservice.domain.result.ValidateTokenResult;

public interface ValidateTokenPort {
    ValidateTokenResult validateToken(String token);
}
