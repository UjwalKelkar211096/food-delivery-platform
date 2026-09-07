package com.fooddelivery.fooddeliveryplatform.dto;

import com.fooddelivery.fooddeliveryplatform.entity.RestaurantType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class RestaurantRequest {

    // Restaurant name is mandatory and cannot be empty or only whitespace.
    @NotBlank(message = "Restaurant name is required")
    private String name;

    // Restaurant address is mandatory.
    @NotBlank(message = "Restaurant address is required")
    private String address;

    // Email is mandatory and must follow a valid email format.
    @NotBlank(message = "Restaurant email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    // Phone number is mandatory.
    @NotBlank(message = "Restaurant phone number is required")
    private String phoneNumber;

    // Restaurant type is mandatory.
    // @NotNull is used because RestaurantType is an enum, not a String.
    @NotNull(message = "Restaurant type is required")
    private RestaurantType type;


    // Restaurant opening time.
    // LocalTime is used because we need only the time, not the date.
    private LocalTime openingTime;

    // Restaurant closing time.
    // These fields are optional for now.
    private LocalTime closingTime;

}
