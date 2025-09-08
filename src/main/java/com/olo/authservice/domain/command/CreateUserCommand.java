package com.olo.authservice.domain.command;

import com.olo.authservice.domain.exception.user.SuperAdminCreationDeniedException;
import com.olo.exceptions.permissions.InvalidPermissionValueException;
import com.olo.permissions.Role;
import com.olo.permissions.Title;

public record CreateUserCommand(
        String email,
        Role role,
        Title title
) {
    public static CreateUserCommand of(CreateUserCommand command) {
        if (command.role.equals(Role.SUPER_ADMIN) || (command.role.equals(Role.ADMIN) && command.title.equals(Title.SECRETARY))) {
            throw new SuperAdminCreationDeniedException("Creating a user with super-administrator permissions is not allowed.");
        }
        Title finalTitle = (command.title != null) ? command.title : command.role.getDefaultTitle();
        if (!finalTitle.getRole().equals(command.role)) {
            throw new InvalidPermissionValueException("Title and role do not match, invalid values");
        }
        return new CreateUserCommand(command.email(), command.role, finalTitle);
    }
}
