package com.olo.authservice.infrastructure.repositories;

import com.olo.authservice.infrastructure.entities.TokenEntity;
import com.olo.authservice.infrastructure.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaTokenRepository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByJti(String jti);
    List<TokenEntity> findAllByUser(UserEntity user);
}
