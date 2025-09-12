package com.olo.authservice.infrastructure.mappers;

import com.olo.authservice.domain.command.TitleCommand;
import com.olo.authservice.infrastructure.dtos.request.TitleRequestDto;

public class PermissionMapper {

    public static TitleCommand requestDtoToCommand(TitleRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }

        return new TitleCommand(
                requestDto.userId(),
                requestDto.title()
        );
    }
}
