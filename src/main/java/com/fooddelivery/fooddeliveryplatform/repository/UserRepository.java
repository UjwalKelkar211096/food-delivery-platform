package com.fooddelivery.fooddeliveryplatform.repository;

import com.fooddelivery.fooddeliveryplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // Used when creating a user to check whether the email is already registered.
    boolean existsByEmail(String email);

    // Used when updating a user to check whether another user owns this email.
    boolean existsByEmailAndIdNot(String email, Long id);
}