package com.olo.authservice.domain.port.outbound;

import com.olo.authservice.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {

    boolean existByUsername(String username);

    User save(User user);
    void delete(Long id);

    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByDni(Long dni);
    Optional<User> findSuperAdmin();
}
