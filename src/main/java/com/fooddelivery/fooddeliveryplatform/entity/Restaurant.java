package com.fooddelivery.fooddeliveryplatform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "restaurants")
public class Restaurant {

    /*
     * Primary key of the restaurant.
     * The database automatically generates this value.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Restaurant's display/business name.
     */
    private String name;

    /*
     * Physical address of the restaurant.
     */
    private String address;

    /*
     * Email used for restaurant contact.
     */
    private String email;

    /*
     * Phone number used for restaurant contact.
     * Stored as String because phone numbers are identifiers,
     * not mathematical numbers.
     */
    private String phoneNumber;

    /*
     * Defines whether the restaurant serves vegetarian,
     * non-vegetarian, or both types of food.
     *
     * EnumType.STRING stores values such as "VEG" in the database
     * instead of numeric positions such as 0, 1, or 2.
     */
    @Enumerated(EnumType.STRING)
    private RestaurantType type;

    /*
     * Restaurant opening time.
     * LocalTime is used because opening hours do not depend on a date.
     *
     * This field is optional, so it can be null.
     */
    private LocalTime openingTime;

    /*
     * Restaurant closing time.
     * This field is also optional.
     */
    private LocalTime closingTime;
}