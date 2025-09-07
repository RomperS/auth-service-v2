package com.olo.authservice.domain.port.outbound;

import com.olo.authservice.domain.model.User;
import com.olo.permissions.Role;
import com.olo.permissions.Title;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {

    boolean existByUsername(String username);
    boolean existByEmail(String email);

    User save(User user);
    void delete(Long id);

    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);

    List<User> findAll();
    List<User> findUsersByRole(Role role);
    List<User> findUsersByTitle(Title title);
}
