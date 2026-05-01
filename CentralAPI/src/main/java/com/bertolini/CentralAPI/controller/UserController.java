package com.bertolini.CentralAPI.controller;

import com.bertolini.CentralAPI.domain.User;
import com.bertolini.CentralAPI.schema.page.PageResponse;
import com.bertolini.CentralAPI.schema.user.*;
import com.bertolini.CentralAPI.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<UserResponse>> getUsers(Pageable pageable) {
        Page<UserResponse> page = userService.findAll(pageable).map(UserResponse::new);
        return ResponseEntity.ok(new PageResponse<>(page));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        User user = userService.findByUserId(userId);
        UserResponse userResponse = new UserResponse(user);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/remain-requests")
    public ResponseEntity<RemainingRequestsDTO> getRemainingRequests(@AuthenticationPrincipal User loggedUser) {
        return ResponseEntity.ok(new RemainingRequestsDTO(loggedUser));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest, UriComponentsBuilder uriComponentsBuilder) {
        User user = new User(userRequest);
        userService.save(user);
        URI uri = uriComponentsBuilder.path("/user/{user_id}").buildAndExpand(user.getUserId()).toUri();

        return ResponseEntity.created(uri).body(new UserResponse(user));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<UserResponse> deleteUser(@AuthenticationPrincipal User loggedUser) {
        userService.delete(loggedUser);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<UserResponse> updateUser(@AuthenticationPrincipal User loggedUser, @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.update(loggedUser, userUpdateRequest);
        return ResponseEntity.ok(new UserResponse(loggedUser));
    }

    @PutMapping("/role")
    @Transactional
    public ResponseEntity<UserResponse> updateUserRole(@AuthenticationPrincipal User loggedUser, @RequestBody UserUpdateRoleRequest request) {
        userService.updateRole(loggedUser, request);
        return ResponseEntity.ok(new UserResponse(loggedUser));
    }
}
