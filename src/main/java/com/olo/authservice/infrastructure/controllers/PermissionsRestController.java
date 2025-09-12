package com.olo.authservice.infrastructure.controllers;

import com.olo.authservice.application.service.PermissionService;
import com.olo.authservice.domain.command.TitleCommand;
import com.olo.authservice.infrastructure.dtos.request.TitleRequestDto;
import com.olo.authservice.infrastructure.mappers.PermissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionsRestController {

    private final PermissionService permissionService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> assignBoardRole(@PathVariable Long userId) {
        permissionService.assignBoardRole(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove/{userId}")
    public ResponseEntity<?> revokeBoardRole(@PathVariable Long userId) {
        permissionService.revokeBoardRole(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/title/add")
    public ResponseEntity<?> addTitle(@RequestBody TitleRequestDto requestDto) {
        TitleCommand command = PermissionMapper.requestDtoToCommand(requestDto);
        command.verifyTitle();
        permissionService.addTitle(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/title/remove")
    public ResponseEntity<?> removeTitle(@RequestBody TitleRequestDto requestDto) {
        TitleCommand command = PermissionMapper.requestDtoToCommand(requestDto);
        command.verifyTitle();
        permissionService.removeTitle(command);
        return ResponseEntity.ok().build();
    }
}
