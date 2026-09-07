package com.fooddelivery.fooddeliveryplatform.service;

import com.fooddelivery.fooddeliveryplatform.dto.RestaurantRequest;
import com.fooddelivery.fooddeliveryplatform.dto.RestaurantResponse;
import com.fooddelivery.fooddeliveryplatform.entity.Restaurant;
import com.fooddelivery.fooddeliveryplatform.exception.DuplicateEmailException;
import com.fooddelivery.fooddeliveryplatform.exception.DuplicatePhoneException;
import com.fooddelivery.fooddeliveryplatform.exception.RestaurantNotFoundException;
import com.fooddelivery.fooddeliveryplatform.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    /*
     * Constructor Injection:
     * Spring automatically provides the RestaurantRepository object
     * when it creates the RestaurantService object.
     *
     * We use constructor injection because it makes the dependency
     * explicit and allows the field to be final.
     */
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /*
     * Creates a new restaurant.
     *
     * The method:
     * 1. Checks whether the email already exists.
     * 2. Checks whether the phone number already exists.
     * 3. Converts RestaurantRequest (DTO) into Restaurant (Entity).
     * 4. Saves the entity to the database.
     * 5. Converts the saved entity into RestaurantResponse (DTO).
     */
    public RestaurantResponse createRestaurant(RestaurantRequest request) {

        /*
         * Business Rule:
         * Restaurant email must be unique.
         *
         * If the email already exists, stop the operation immediately
         * and inform the client through a custom exception.
         */
        if (restaurantRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Restaurant email already exists");
        }

        /*
         * Business Rule:
         * Restaurant phone number must also be unique.
         *
         * We check this only after the email has passed validation.
         */
        if (restaurantRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new DuplicatePhoneException("Restaurant phone number already exists");
        }

        /*
         * Convert the incoming DTO into a Restaurant Entity.
         *
         * The DTO represents data received from the client,
         * while the Entity represents data that will be stored
         * in the database.
         */
        Restaurant restaurant = new Restaurant();

        restaurant.setName(request.getName());
        restaurant.setAddress(request.getAddress());
        restaurant.setEmail(request.getEmail());
        restaurant.setPhoneNumber(request.getPhoneNumber());
        restaurant.setType(request.getType());
        restaurant.setOpeningTime(request.getOpeningTime());
        restaurant.setClosingTime(request.getClosingTime());

        /*
         * Save the Restaurant entity to the database.
         *
         * The database generates the ID because the entity uses
         * @GeneratedValue(strategy = GenerationType.IDENTITY).
         *
         * save() returns the persisted entity, including the generated ID.
         */
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        /*
         * Convert the saved Entity into a Response DTO.
         *
         * We return the Response DTO instead of directly exposing
         * the database Entity to the client.
         */
        return new RestaurantResponse(
                savedRestaurant.getId(),
                savedRestaurant.getName(),
                savedRestaurant.getAddress(),
                savedRestaurant.getEmail(),
                savedRestaurant.getPhoneNumber(),
                savedRestaurant.getType(),
                savedRestaurant.getOpeningTime(),
                savedRestaurant.getClosingTime()
        );
    }

    /*
     * Fetches all restaurants from the database.
     *
     * The repository returns a List of Restaurant entities.
     * We convert each entity into RestaurantResponse so that
     * the API does not expose database entities directly.
     */
    public List<RestaurantResponse> getAllRestaurants() {

        return restaurantRepository.findAll()
                .stream()
                .map(restaurant -> new RestaurantResponse(
                        restaurant.getId(),
                        restaurant.getName(),
                        restaurant.getAddress(),
                        restaurant.getEmail(),
                        restaurant.getPhoneNumber(),
                        restaurant.getType(),
                        restaurant.getOpeningTime(),
                        restaurant.getClosingTime()
                ))
                .toList();
    }

    /*
     * Fetches a restaurant by its ID.
     *
     * findById() returns Optional because the restaurant may not exist.
     * If the restaurant is not found, we throw RestaurantNotFoundException
     * so that the GlobalExceptionHandler can return HTTP 404.
     */
    public RestaurantResponse getRestaurantById(Long id) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new RestaurantNotFoundException(
                                "Restaurant not found with id: " + id
                        )
                );

        /*
         * Convert the database Entity into a Response DTO
         * before returning the data to the client.
         */
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getEmail(),
                restaurant.getPhoneNumber(),
                restaurant.getType(),
                restaurant.getOpeningTime(),
                restaurant.getClosingTime()
        );
    }

    /*
     * Updates an existing restaurant.
     *
     * The method:
     * 1. Finds the restaurant using the provided ID.
     * 2. Checks whether the new email belongs to another restaurant.
     * 3. Checks whether the new phone number belongs to another restaurant.
     * 4. Updates the existing entity.
     * 5. Saves the updated entity.
     * 6. Converts the entity into RestaurantResponse.
     */
    public RestaurantResponse updateRestaurant(Long id, RestaurantRequest request) {

        /*
         * First, verify that the restaurant actually exists.
         *
         * If it doesn't exist, RestaurantNotFoundException is thrown
         * and our GlobalExceptionHandler converts it to HTTP 404.
         */
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new RestaurantNotFoundException(
                                "Restaurant not found with id: " + id
                        )
                );

        /*
         * Check email uniqueness while excluding the current restaurant.
         *
         * This is important because a restaurant is allowed to keep
         * its own existing email during an update.
         */
        if (restaurantRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new DuplicateEmailException(
                    "Restaurant email already exists"
            );
        }

        /*
         * Check phone uniqueness while excluding the current restaurant.
         */
        if (restaurantRepository.existsByPhoneNumberAndIdNot(
                request.getPhoneNumber(), id)) {

            throw new DuplicatePhoneException(
                    "Restaurant phone number already exists"
            );
        }

        /*
         * Update the existing entity with the values received
         * from the request.
         */
        restaurant.setName(request.getName());
        restaurant.setAddress(request.getAddress());
        restaurant.setEmail(request.getEmail());
        restaurant.setPhoneNumber(request.getPhoneNumber());
        restaurant.setType(request.getType());
        restaurant.setOpeningTime(request.getOpeningTime());
        restaurant.setClosingTime(request.getClosingTime());

        /*
         * Save the updated entity.
         *
         * Since this entity already has an ID, JPA updates the
         * existing database record instead of creating a new one.
         */
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        /*
         * Return a DTO instead of exposing the Entity directly.
         */
        return new RestaurantResponse(
                updatedRestaurant.getId(),
                updatedRestaurant.getName(),
                updatedRestaurant.getAddress(),
                updatedRestaurant.getEmail(),
                updatedRestaurant.getPhoneNumber(),
                updatedRestaurant.getType(),
                updatedRestaurant.getOpeningTime(),
                updatedRestaurant.getClosingTime()
        );
    }


}