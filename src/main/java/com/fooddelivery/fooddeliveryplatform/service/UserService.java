package com.fooddelivery.fooddeliveryplatform.service;

import com.fooddelivery.fooddeliveryplatform.dto.UserRequest;
import com.fooddelivery.fooddeliveryplatform.entity.User;
import com.fooddelivery.fooddeliveryplatform.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserRequest userRequest) {

        User user = new User();

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());

        return userRepository.save(user);
    }
}
