package com.olo.authservice.domain.command;


import com.olo.internalauthlibrary.exceptions.permissions.InvalidPermissionValueException;
import com.olo.internalauthlibrary.permissions.Role;
import com.olo.internalauthlibrary.permissions.Title;

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
