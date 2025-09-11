package com.olo.authservice.domain.port.inbound.users;

import com.olo.authservice.domain.model.User;

public interface GetUserByDniPort {
    User getUserByDni(Long dni);
}
