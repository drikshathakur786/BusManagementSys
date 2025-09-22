package com.example.demo.repository;

import com.example.demo.model.Route;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends MongoRepository<Route, String> {

    // Find route by bus ID
    Route findByBusId(String busId);

    // Find route by route code
    Optional<Route> findByRouteCode(String routeCode);

    // Find routes by start location
    List<Route> findByStartLocation(String startLocation);

    // Find routes by end location
    List<Route> findByEndLocation(String endLocation);

    // Find routes between two locations
    List<Route> findByStartLocationAndEndLocation(String startLocation, String endLocation);

    // Find active routes only
    List<Route> findByIsActiveTrue();

    // Find inactive routes
    List<Route> findByIsActiveFalse();

    // Find routes by route name (case-insensitive)
    @Query("{'routeName': {$regex: ?0, $options: 'i'}}")
    List<Route> findByRouteNameContainingIgnoreCase(String routeName);

    // Find routes that contain a specific stop
    @Query("{'stops': {$in: [?0]}}")
    List<Route> findByStopsContaining(String stop);

    // Find routes within a fare range
    List<Route> findByFareBetween(Double minFare, Double maxFare);

    // Find routes departing after a specific time
    List<Route> findByDepartureTimeAfter(LocalTime time);

    // Find routes departing before a specific time
    List<Route> findByDepartureTimeBefore(LocalTime time);

    // Find routes by distance range
    List<Route> findByDistanceBetween(Double minDistance, Double maxDistance);

    // Custom query to find routes with multiple conditions
    @Query("{'isActive': true, 'startLocation': ?0, 'endLocation': ?1, 'departureTime': {$gte: ?2}}")
    List<Route> findActiveRoutesByLocationsAndDepartureTime(String startLocation, String endLocation, LocalTime departureTime);

    // Find routes by multiple stop locations
    @Query("{'stops': {$all: ?0}}")
    List<Route> findByAllStopsIn(List<String> stops);

    // Count active routes
    long countByIsActiveTrue();

    // Check if route code exists
    boolean existsByRouteCode(String routeCode);

    // Check if bus is assigned to any route
    boolean existsByBusId(String busId);

    // Delete routes by bus ID
    void deleteByBusId(String busId);
}