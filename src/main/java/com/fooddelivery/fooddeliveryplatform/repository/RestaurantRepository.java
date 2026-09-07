package com.fooddelivery.fooddeliveryplatform.repository;

import com.fooddelivery.fooddeliveryplatform.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // Checks whether another restaurant already uses this email.
    boolean existsByEmail(String email);

    // Checks whether another restaurant already uses this phone number.
    boolean existsByPhoneNumber(String phoneNumber);

    // Used during update: checks email duplication while excluding the current restaurant.
    boolean existsByEmailAndIdNot(String email, Long id);

    // Used during update: checks phone duplication while excluding the current restaurant.
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);
}