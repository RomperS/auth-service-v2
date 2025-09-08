package com.olo.authservice.infrastructure.adapters;

import com.olo.authservice.domain.model.User;
import com.olo.authservice.domain.port.outbound.UserRepositoryPort;
import com.olo.authservice.infrastructure.entities.UserEntity;
import com.olo.authservice.infrastructure.mappers.UserMapper;
import com.olo.authservice.infrastructure.repositories.JpaUserRepository;
import com.olo.permissions.Role;
import com.olo.permissions.Title;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
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
    public boolean existByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
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
    public List<User> findAll() {
        return jpaUserRepository.findAll()
                .stream()
                .map(UserMapper::entityToModel)
                .toList();
    }

    @Override
    public List<User> findUsersByRole(Role role) {

        return jpaUserRepository.findByRole(role)
                .stream()
                .map(UserMapper::entityToModel)
                .toList();
    }

    @Override
    public List<User> findUsersByTitle(Title title) {
        return jpaUserRepository.findByTitle(title)
                .stream()
                .map(UserMapper::entityToModel)
                .toList();
    }
}
