package com.fooddelivery.fooddeliveryplatform.controller;

import com.fooddelivery.fooddeliveryplatform.dto.UserRequest;
import com.fooddelivery.fooddeliveryplatform.dto.UserResponse;
import com.fooddelivery.fooddeliveryplatform.entity.User;
import com.fooddelivery.fooddeliveryplatform.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/users")
    public UserResponse createUser(@Valid @RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @GetMapping("/api/users")
    public List<UserResponse> getAllUsers() {

        return userService.findAllUsers();
    }
    @GetMapping("/api/users/{id}")
    public UserResponse getUserById(@PathVariable Long id) {

        return userService.getUserById(id);
    }
    @PutMapping("/api/users/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest userRequest) {

        return userService.updateUser(id, userRequest);
    }
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        // 204 means the operation succeeded and there is no response body.
        return ResponseEntity.noContent().build();
    }
}
