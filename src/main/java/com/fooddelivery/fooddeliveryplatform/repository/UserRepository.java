package com.fooddelivery.fooddeliveryplatform.repository;

import com.fooddelivery.fooddeliveryplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
