package com.olo.authservice.domain.port.inbound.users;

public interface LockUserPort {
    void lockUser(Long userId);
}
