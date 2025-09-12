package com.olo.authservice.infrastructure.dtos.response;

import com.olo.permissions.*;

import java.util.List;

public record UserResponseDto(
        Long id,
        String username,
        List<Role> roles,
        List<Title> titles
) {
}
