package com.olo.authservice.domain.model;

import com.olo.internalauthlibrary.permissions.Role;
import com.olo.internalauthlibrary.permissions.Title;

import java.util.List;

public record User(
        Long id,
        String username,
        Long dni,
        String password,
        Boolean accountLocked,
        List<Role> roles,
        List<Title> titles
) {
}