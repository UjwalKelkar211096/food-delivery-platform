package com.fooddelivery.fooddeliveryplatform.dto;

import com.fooddelivery.fooddeliveryplatform.entity.RestaurantType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantResponse {

    private Long id;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private RestaurantType type;
    private LocalTime openingTime;
    private LocalTime closingTime;
}
