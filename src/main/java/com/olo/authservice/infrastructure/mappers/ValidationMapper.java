package com.olo.authservice.infrastructure.mappers;

import com.olo.authservice.domain.command.AuthCommand;
import com.olo.authservice.domain.result.AuthResult;
import com.olo.authservice.domain.result.ValidateTokenResult;
import com.olo.authservice.infrastructure.dtos.request.LoginRequestDto;
import com.olo.authservice.infrastructure.dtos.request.SignupRequestDto;
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

    public static AuthCommand loginRequestToLoginCommand(LoginRequestDto loginRequestDto){
        if (loginRequestDto == null){
            return null;
        }

        return new AuthCommand(
                loginRequestDto.username(),
                loginRequestDto.password()
        );
    }

    public static AuthCommand createUserRequestToCreateUserCommand(SignupRequestDto signupRequestDto){
        if (signupRequestDto == null){
            return null;
        }

        return new AuthCommand(
                signupRequestDto.username(),
                signupRequestDto.password()
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
