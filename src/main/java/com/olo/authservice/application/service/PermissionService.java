package com.olo.authservice.application.service;

import com.olo.authservice.application.usecase.permissions.AddTitleImpl;
import com.olo.authservice.application.usecase.permissions.AssignBoardRoleImpl;
import com.olo.authservice.application.usecase.permissions.RemoveTitleImpl;
import com.olo.authservice.application.usecase.permissions.RevokeBoardRoleImpl;
import com.olo.authservice.domain.command.TitleCommand;
import com.olo.authservice.domain.port.inbound.permissions.AddTitlePort;
import com.olo.authservice.domain.port.inbound.permissions.AssignBoardRolePort;
import com.olo.authservice.domain.port.inbound.permissions.RemoveTitlePort;
import com.olo.authservice.domain.port.inbound.permissions.RevokeBoardRolePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PermissionService implements AssignBoardRolePort, RevokeBoardRolePort, AddTitlePort, RemoveTitlePort {

    private final AssignBoardRoleImpl assignBoardRoleImpl;
    private final RevokeBoardRoleImpl revokeBoardRoleImpl;
    private final AddTitleImpl addTitleImpl;
    private final RemoveTitleImpl removeTitleImpl;

    @Override
    public void assignBoardRole(Long userId) {
        assignBoardRoleImpl.assignBoardRole(userId);
    }

    @Override
    public void revokeBoardRole(Long userId) {
        revokeBoardRoleImpl.revokeBoardRole(userId);
    }

    @Override
    public void addTitle(TitleCommand command) {
        addTitleImpl.addTitle(command);
    }

    @Override
    public void removeTitle(TitleCommand command) {
        removeTitleImpl.removeTitle(command);
    }
}
