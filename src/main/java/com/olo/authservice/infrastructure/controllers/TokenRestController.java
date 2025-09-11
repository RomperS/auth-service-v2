package com.olo.authservice.infrastructure.controllers;

import com.olo.authservice.application.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
public class TokenRestController {

    private final TokenService tokenService;

    @GetMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String refreshToken = token.substring(7);
        String accessToken = tokenService.generateAccessToken(refreshToken);
        return ResponseEntity.ok(accessToken);
    }
}
