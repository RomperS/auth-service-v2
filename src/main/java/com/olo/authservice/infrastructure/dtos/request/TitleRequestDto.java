package com.olo.authservice.infrastructure.dtos.request;

import com.olo.permissions.Title;

public record TitleRequestDto(
        Long userId,
        Title title
) {
}
