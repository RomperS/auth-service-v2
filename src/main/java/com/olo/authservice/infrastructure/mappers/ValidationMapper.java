package com.olo.authservice.infrastructure.mappers;

import com.olo.authservice.domain.command.AuthCommand;
import com.olo.authservice.domain.result.AuthResult;
import com.olo.authservice.domain.result.ValidateTokenResult;
import com.olo.authservice.infrastructure.dtos.request.AuthRequestDto;
import com.olo.authservice.infrastructure.dtos.response.*;

public class ValidationMapper {

    public static AuthResponseDto authResultToResponseDto(AuthResult authResult){
        if(authResult == null){
            return null;
        }

        return new AuthResponseDto(
                authResult.refreshToken(),
                authResult.expireAt(),
                new AccessTokenResponseDto(
                        authResult.accessToken().token(),
                        authResult.accessToken().expireAt()
                )
        );
    }

    public static AuthCommand authRequestToAuthCommand(AuthRequestDto authRequestDto){
        if (authRequestDto == null){
            return null;
        }

        return new AuthCommand(
                authRequestDto.username(),
                authRequestDto.password()
        );
    }

    public static ValidateTokenResponseDto validateTokenResultToResponseDto(ValidateTokenResult validateTokenResult){
        if (validateTokenResult == null){
            return null;
        }

        return new ValidateTokenResponseDto(
                validateTokenResult.isValid(),
                validateTokenResult.type()
        );
    }
}
