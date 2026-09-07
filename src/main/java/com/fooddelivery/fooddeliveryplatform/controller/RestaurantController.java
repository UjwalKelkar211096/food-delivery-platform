package com.fooddelivery.fooddeliveryplatform.controller;

import com.fooddelivery.fooddeliveryplatform.dto.RestaurantRequest;
import com.fooddelivery.fooddeliveryplatform.dto.RestaurantResponse;
import com.fooddelivery.fooddeliveryplatform.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    /*
     * Constructor Injection:
     * Spring automatically injects RestaurantService here.
     */
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /*
     * Creates a new restaurant.
     *
     * @RequestBody converts the incoming JSON into RestaurantRequest.
     *
     * @Valid triggers the validation annotations defined
     * inside RestaurantRequest.
     *
     * ResponseEntity allows us to explicitly return HTTP 201 CREATED
     * along with the RestaurantResponse.
     */
    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(
            @Valid @RequestBody RestaurantRequest request) {

        RestaurantResponse response =
                restaurantService.createRestaurant(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {

        return ResponseEntity.ok(
                restaurantService.getAllRestaurants()
        );
    }

    /*
     * Fetches a single restaurant using its unique ID.
     *
     * @PathVariable extracts the ID from the URL.
     *
     * Example:
     * GET /api/restaurants/1
     *
     * The controller delegates the actual business logic
     * to RestaurantService.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(
            @PathVariable Long id) {

        RestaurantResponse response =
                restaurantService.getRestaurantById(id);

        return ResponseEntity.ok(response);
    }

    /*
     * Updates an existing restaurant.
     *
     * @PathVariable gets the restaurant ID from the URL.
     * @Valid validates the incoming RestaurantRequest.
     *
     * Example:
     * PUT /api/restaurants/1
     */
    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantRequest request) {

        RestaurantResponse response =
                restaurantService.updateRestaurant(id, request);

        /*
         * HTTP 200 OK indicates that the existing restaurant
         * was successfully updated.
         */
        return ResponseEntity.ok(response);
    }
}