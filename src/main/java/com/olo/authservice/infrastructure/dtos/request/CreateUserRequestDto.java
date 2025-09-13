package com.olo.authservice.infrastructure.dtos.request;

import com.olo.internalauthlibrary.permissions.*;

public record CreateUserRequestDto(
        String email,
        Role role,
        Title title
) {
}