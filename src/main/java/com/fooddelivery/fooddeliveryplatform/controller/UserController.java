package com.fooddelivery.fooddeliveryplatform.controller;

import com.fooddelivery.fooddeliveryplatform.dto.UserRequest;
import com.fooddelivery.fooddeliveryplatform.entity.User;
import com.fooddelivery.fooddeliveryplatform.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/users")
    public User createUser(@RequestBody UserRequest userRequest){
        return userService.createUser(userRequest);
    }
}
