package com.olo.authservice.infrastructure.dtos.request;

import com.olo.internalauthlibrary.permissions.Title;

public record TitleRequestDto(
        Long userId,
        Title title
) {
}
