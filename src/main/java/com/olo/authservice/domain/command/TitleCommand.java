package com.olo.authservice.domain.command;

import com.olo.exceptions.permissions.InvalidPermissionValueException;
import com.olo.permissions.Role;
import com.olo.permissions.Title;

public record TitleCommand(
        Long userId,
        Title title
) {

    public void verifyTitle(){
        Role role = title.getRole();
        Title defaultTitle = role.getDefaultTitle();
        if(defaultTitle == title){
            throw new InvalidPermissionValueException("Cannot add or remove a default title");
        }
        if (title.equals(Title.DIRECTORS_BOARD)){
            throw new InvalidPermissionValueException("Cannot add or remove DIRECTORS_BOARD title");
        }
    }
}
