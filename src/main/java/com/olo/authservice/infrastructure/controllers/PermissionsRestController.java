package com.olo.authservice.infrastructure.controllers;

import com.olo.authservice.application.service.PermissionService;
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
}
