package com.olo.authservice.application.service;

import com.olo.authservice.application.usecase.permissions.AssignBoardRoleImpl;
import com.olo.authservice.application.usecase.permissions.RevokeBoardRoleImpl;
import com.olo.authservice.domain.port.inbound.permissions.AssignBoardRolePort;
import com.olo.authservice.domain.port.inbound.permissions.RevokeBoardRolePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PermissionService implements AssignBoardRolePort, RevokeBoardRolePort {

    private final AssignBoardRoleImpl assignBoardRoleImpl;
    private final RevokeBoardRoleImpl revokeBoardRoleImpl;

    @Override
    public void assignBoardRole(Long userId) {
        assignBoardRoleImpl.assignBoardRole(userId);
    }

    @Override
    public void revokeBoardRole(Long userId) {
        revokeBoardRoleImpl.revokeBoardRole(userId);
    }
}
