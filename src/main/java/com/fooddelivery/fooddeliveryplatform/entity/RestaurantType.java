package com.fooddelivery.fooddeliveryplatform.entity;

/*
 * Defines the allowed restaurant food types.
 *
 * Using an enum prevents invalid/inconsistent values such as
 * "veg", "Veg", "VEG", "vegetarian", etc.
 */
public enum RestaurantType {

    VEG,
    NON_VEG,
    BOTH
}