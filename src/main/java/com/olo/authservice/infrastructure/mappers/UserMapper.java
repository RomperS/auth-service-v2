package com.olo.authservice.infrastructure.mappers;

import com.olo.authservice.domain.model.User;
import com.olo.authservice.infrastructure.entities.UserEntity;

public class UserMapper {

    public static User entityToModel(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        return new User(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.isAccountLocked(),
                userEntity.getRoles(),
                userEntity.getTitles()
        );
    }

    public static UserEntity modelToEntity(User user) {
        if (user == null) {
            return null;
        }

        return new UserEntity(
                user.id(),
                user.username(),
                user.email(),
                user.password(),
                user.accountLocked(),
                user.roles(),
                user.titles()
        );
    }
}
