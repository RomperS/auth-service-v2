package com.olo.authservice.infrastructure.mappers;

import com.olo.authservice.domain.command.CreateUserCommand;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.infrastructure.dtos.request.CreateUserRequestDto;
import com.olo.authservice.infrastructure.entities.UserEntity;

public class UserMapper {

    public static User entityToModel(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        return new User(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getDni(),
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
                user.dni(),
                user.password(),
                user.accountLocked(),
                user.roles(),
                user.titles()
        );
    }

    public static CreateUserCommand createRequestDtoToCommand(CreateUserRequestDto createUserRequestDto) {
        if (createUserRequestDto == null) {
            return null;
        }

        return new CreateUserCommand(
                createUserRequestDto.email(),
                createUserRequestDto.role(),
                createUserRequestDto.title()
        );
    }
}
