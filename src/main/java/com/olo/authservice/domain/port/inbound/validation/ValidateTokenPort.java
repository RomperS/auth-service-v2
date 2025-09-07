package com.olo.authservice.domain.port.inbound.validation;

import com.olo.authservice.domain.result.AccessTokenResult;

public interface ValidateTokenPort {
    AccessTokenResult validateToken(String token);
}
