package com.olo.authservice.infrastructure.repositories;

import com.olo.authservice.infrastructure.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
    boolean existsByDni(Long dni);

    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r = 'SUPER_ADMIN'")
    Optional<UserEntity> findAdmin();

    Optional<UserEntity> findByDni(Long dni);
}
