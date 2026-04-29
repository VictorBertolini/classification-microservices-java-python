package com.bertolini.CentralAPI.controller;

import com.bertolini.CentralAPI.domain.User;
import com.bertolini.CentralAPI.schema.page.PageResponse;
import com.bertolini.CentralAPI.schema.user.RemainingRequestsDTO;
import com.bertolini.CentralAPI.schema.user.UserRequest;
import com.bertolini.CentralAPI.schema.user.UserResponse;
import com.bertolini.CentralAPI.schema.user.UserUpdateRequest;
import com.bertolini.CentralAPI.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{userId}/remain-requests")
    public ResponseEntity<RemainingRequestsDTO> getRemainingRequests(@PathVariable Long userId) {
        User user = userService.findByUserId(userId);
        return ResponseEntity.ok(new RemainingRequestsDTO(user));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest, UriComponentsBuilder uriComponentsBuilder) {
        User user = new User(userRequest);
        userService.save(user);
        URI uri = uriComponentsBuilder.path("/user/{user_id}").buildAndExpand(user.getUserId()).toUri();

        return ResponseEntity.created(uri).body(new UserResponse(user));
    }

    @DeleteMapping("/{userId}")
    @Transactional
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    @Transactional
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        User user = userService.findByUserId(userId);
        user.update(userUpdateRequest);
        return ResponseEntity.ok(new UserResponse(user));
    }

}
