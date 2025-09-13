package com.olo.authservice.infrastructure.adapters.repository;

import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.outbound.UserRepositoryPort;
import com.olo.authservice.infrastructure.entities.UserEntity;
import com.olo.authservice.infrastructure.mappers.UserMapper;
import com.olo.authservice.infrastructure.repositories.JpaUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public boolean existByUsername(String username) {
        return jpaUserRepository.existsByUsername(username);
    }

    @Override
    public boolean existByDni(Long dni) {
        return jpaUserRepository.existsByDni(dni);
    }

    @Override
    @Transactional
    public User save(User user) {
        UserEntity userEntity = UserMapper.modelToEntity(user);
        return UserMapper.entityToModel(jpaUserRepository.save(userEntity));
    }

    @Override
    public void delete(Long id) {
        jpaUserRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id)
                .map(UserMapper::entityToModel);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaUserRepository.findByUsername(username)
                .map(UserMapper::entityToModel);
    }

    @Override
    public Optional<User> findByDni(Long dni) {
        return jpaUserRepository.findByDni(dni).map(UserMapper::entityToModel);
    }

    @Override
    public Optional<User> findSuperAdmin() {
        return jpaUserRepository.findAdmin().map(UserMapper::entityToModel);
    }
}
