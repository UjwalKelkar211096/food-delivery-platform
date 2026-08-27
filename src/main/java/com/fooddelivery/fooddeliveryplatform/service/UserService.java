package com.fooddelivery.fooddeliveryplatform.service;

import com.fooddelivery.fooddeliveryplatform.dto.UserRequest;
import com.fooddelivery.fooddeliveryplatform.dto.UserResponse;
import com.fooddelivery.fooddeliveryplatform.entity.User;
import com.fooddelivery.fooddeliveryplatform.exception.DuplicateEmailException;
import com.fooddelivery.fooddeliveryplatform.exception.UserNotFoundException;
import com.fooddelivery.fooddeliveryplatform.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest userRequest) {
        // Prevent creation of multiple users with the same email.
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new DuplicateEmailException(
                    "User with email " + userRequest.getEmail() + " already exists"
            );
        }

        User user = new User();

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail(),
                savedUser.getPhoneNumber()
        );
    }

    public List<UserResponse> findAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPhoneNumber()
                ))
                .toList();
    }
    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + id)
                );

        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }
    public UserResponse updateUser(Long id, UserRequest userRequest) {

        // Find the existing user. If the user does not exist,
        // the service throws our custom 404 exception.
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + id)
                );

        // Prevent the user from changing their email to one already used by another user.
        if (userRepository.existsByEmailAndIdNot(userRequest.getEmail(), id)) {
            throw new DuplicateEmailException(
                    "User with email " + userRequest.getEmail() + " already exists"
            );
        }

        // Update the existing entity with the values received from the client.
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());

        User updatedUser = userRepository.save(user);

        return new UserResponse(
                updatedUser.getId(),
                updatedUser.getFirstName(),
                updatedUser.getLastName(),
                updatedUser.getEmail(),
                updatedUser.getPhoneNumber()
        );
    }

    public void deleteUser(Long id) {

        // Check that the user exists before attempting to delete.
        // This allows us to return our custom 404 error instead of silently doing nothing.
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + id)
                );

        // Delete the existing user from the database.
        userRepository.delete(user);
    }
}
