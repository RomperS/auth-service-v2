package com.olo.authservice.infrastructure.controllers;

import com.olo.authservice.application.service.ValidationService;
import com.olo.authservice.domain.result.AuthResult;
import com.olo.authservice.domain.result.ValidateTokenResult;
import com.olo.authservice.infrastructure.dtos.request.LoginRequestDto;
import com.olo.authservice.infrastructure.dtos.request.SignupRequestDto;
import com.olo.authservice.infrastructure.dtos.response.AuthResponseDto;
import com.olo.authservice.infrastructure.dtos.response.ValidateTokenResponseDto;
import com.olo.authservice.infrastructure.mappers.ValidationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/validation")
@RequiredArgsConstructor
public class ValidationRestController {

    private final ValidationService validationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        AuthResult result = validationService.login(ValidationMapper.loginRequestToLoginCommand(requestDto));
        return ResponseEntity.ok().body(ValidationMapper.authResultToResponseDto(result));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String authToken = token.substring(7);
        validationService.logout(authToken);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> signup(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody SignupRequestDto requestDto) {
        String authToken = token.substring(7);
        AuthResult result = validationService.signup(ValidationMapper.createUserRequestToCreateUserCommand(requestDto), authToken);
        return ResponseEntity.ok().body(ValidationMapper.authResultToResponseDto(result));
    }

    @GetMapping("/token")
    public ResponseEntity<ValidateTokenResponseDto> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String authToken = token.substring(7);
        ValidateTokenResult result = validationService.validateToken(authToken);
        return ResponseEntity.ok().body(ValidationMapper.validateTokenResultToResponseDto(result));
    }
}
