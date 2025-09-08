package com.olo.authservice.infrastructure.adapters;

import com.olo.authservice.domain.port.outbound.CommonStringPort;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class CommonStringAdapter implements CommonStringPort {

    @Override
    public String randomAlphanumeric(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
