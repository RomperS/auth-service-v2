package com.olo.authservice.infrastructure.controllers;

import com.olo.authservice.application.service.UserService;
import com.olo.authservice.domain.command.UpdateUserCommand;
import com.olo.authservice.domain.model.User;
import com.olo.authservice.infrastructure.dtos.request.CreateSecretaryRequestDto;
import com.olo.authservice.infrastructure.dtos.request.UpdateUserRequestDto;
import com.olo.authservice.infrastructure.dtos.response.UserResponseDto;
import com.olo.authservice.infrastructure.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping("/lock/{userId}")
    public ResponseEntity<?> lockUser(@PathVariable Long userId) {
        userService.lockUser(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unlock/{userId}")
    public ResponseEntity<?> unlockUser(@PathVariable Long userId) {
        userService.unlockUser(userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, UpdateUserRequestDto requestDto) {
        UpdateUserCommand command = UserMapper.updateRequestDtoToCommand(requestDto, id);
        UserResponseDto response = UserMapper.userToResponseDto(userService.updateUser(command));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{dni}")
    public ResponseEntity<UserResponseDto> getUserByDni(@PathVariable Long dni) {
        UserResponseDto response = UserMapper.userToResponseDto(userService.getUserByDni(dni));
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/secretary")
    public ResponseEntity<UserResponseDto> createUserSecretary(@RequestBody CreateSecretaryRequestDto requestDto) {
        User secretary = userService.createSecretary(UserMapper.secretaryRequestDtoToCommand(requestDto));
        return ResponseEntity.ok().body(UserMapper.userToResponseDto(secretary));
    }
}
