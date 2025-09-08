package com.olo.authservice.infrastructure.dtos.request;

import com.olo.permissions.Role;
import com.olo.permissions.Title;

public record CreateUserRequestDto(
        String email,
        Role role,
        Title title
) {
}