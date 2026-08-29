package com.fooddelivery.fooddeliveryplatform.controller;

import com.fooddelivery.fooddeliveryplatform.common.ApiResponse;
import com.fooddelivery.fooddeliveryplatform.dto.UserRequest;
import com.fooddelivery.fooddeliveryplatform.dto.UserResponse;
import com.fooddelivery.fooddeliveryplatform.entity.User;
import com.fooddelivery.fooddeliveryplatform.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody UserRequest userRequest) {

        UserResponse userResponse = userService.createUser(userRequest);

        ApiResponse<UserResponse> response = new ApiResponse<>(
                true,
                "User created successfully",
                userResponse
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {

        List<UserResponse> users = userService.findAllUsers();

        ApiResponse<List<UserResponse>> response = new ApiResponse<>(
                true,
                "Users fetched successfully",
                users
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @PathVariable Long id) {

        UserResponse userResponse = userService.getUserById(id);

        ApiResponse<UserResponse> response = new ApiResponse<>(
                true,
                "User fetched successfully",
                userResponse
        );

        return ResponseEntity.ok(response);
    }


    @PutMapping("/api/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest userRequest) {

        UserResponse userResponse = userService.updateUser(id, userRequest);

        ApiResponse<UserResponse> response = new ApiResponse<>(
                true,
                "User updated successfully",
                userResponse
        );

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        // Ask the service to delete the user.
        // The service handles the existence check and database deletion.
        userService.deleteUser(id);

        // 204 No Content means the deletion was successful
        // and there is no response body to return.
        return ResponseEntity.noContent().build();
    }
}
