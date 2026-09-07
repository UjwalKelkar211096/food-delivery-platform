package com.fooddelivery.fooddeliveryplatform.exception;

public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
