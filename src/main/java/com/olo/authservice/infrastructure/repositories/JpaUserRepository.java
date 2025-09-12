package com.olo.authservice.infrastructure.repositories;

import com.olo.authservice.infrastructure.entities.UserEntity;
import com.olo.permissions.Role;
import com.olo.permissions.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r = :role")
    List<UserEntity> findByRole(@Param("role") Role role);
    @Query("SELECT u FROM UserEntity u JOIN u.titles t WHERE t = :title")
    List<UserEntity> findByTitle(@Param("title") Title title);

    Optional<UserEntity> findByDni(Long dni);
}
