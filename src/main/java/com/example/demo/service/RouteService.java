package com.example.demo.service;

import com.example.demo.model.Route;
import com.example.demo.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    // Basic CRUD operations
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public Optional<Route> getRouteById(String id) {
        return routeRepository.findById(id);
    }

    public Optional<Route> getRouteByCode(String code) {
        return routeRepository.findByRouteCode(code);
    }

    public Route saveRoute(Route route) {
        return routeRepository.save(route);
    }

    public void deleteRoute(String id) {
        routeRepository.deleteById(id);
    }

    // Enhanced service methods
    public List<Route> getActiveRoutes() {
        return routeRepository.findByIsActiveTrue();
    }

    public List<Route> getInactiveRoutes() {
        return routeRepository.findByIsActiveFalse();
    }

    public Route getRouteByBusId(String busId) {
        return routeRepository.findByBusId(busId);
    }

    public List<Route> getRoutesByStartLocation(String startLocation) {
        return routeRepository.findByStartLocation(startLocation);
    }

    public List<Route> getRoutesByEndLocation(String endLocation) {
        return routeRepository.findByEndLocation(endLocation);
    }

    public List<Route> findRoutesBetweenLocations(String startLocation, String endLocation) {
        return routeRepository.findByStartLocationAndEndLocation(startLocation, endLocation);
    }

    public List<Route> searchRoutesByName(String routeName) {
        return routeRepository.findByRouteNameContainingIgnoreCase(routeName);
    }

    public List<Route> getRoutesContainingStop(String stop) {
        return routeRepository.findByStopsContaining(stop);
    }

    public List<Route> getRoutesByFareRange(Double minFare, Double maxFare) {
        return routeRepository.findByFareBetween(minFare, maxFare);
    }

    public List<Route> getRoutesAfterTime(LocalTime time) {
        return routeRepository.findByDepartureTimeAfter(time);
    }

    public List<Route> getRoutesBeforeTime(LocalTime time) {
        return routeRepository.findByDepartureTimeBefore(time);
    }

    public List<Route> getRoutesByDistanceRange(Double minDistance, Double maxDistance) {
        return routeRepository.findByDistanceBetween(minDistance, maxDistance);
    }

    public List<Route> findAvailableRoutes(String startLocation, String endLocation, LocalTime departureTime) {
        return routeRepository.findActiveRoutesByLocationsAndDepartureTime(startLocation, endLocation, departureTime);
    }

    public List<Route> getRoutesByAllStops(List<String> stops) {
        return routeRepository.findByAllStopsIn(stops);
    }

    // Utility methods
    public long getActiveRouteCount() {
        return routeRepository.countByIsActiveTrue();
    }

    public boolean isRouteCodeExists(String routeCode) {
        return routeRepository.existsByRouteCode(routeCode);
    }

    public boolean isBusAssignedToRoute(String busId) {
        return routeRepository.existsByBusId(busId);
    }

    public void deactivateRoute(String id) {
        Optional<Route> routeOpt = routeRepository.findById(id);
        if (routeOpt.isPresent()) {
            Route route = routeOpt.get();
            route.setIsActive(false);
            routeRepository.save(route);
        }
    }

    public void activateRoute(String id) {
        Optional<Route> routeOpt = routeRepository.findById(id);
        if (routeOpt.isPresent()) {
            Route route = routeOpt.get();
            route.setIsActive(true);
            routeRepository.save(route);
        }
    }

    public void deleteRoutesByBusId(String busId) {
        routeRepository.deleteByBusId(busId);
    }

    // Business logic methods
    public Route createRoute(Route route) {
        // Check if route code already exists
        if (isRouteCodeExists(route.getRouteCode())) {
            throw new RuntimeException("Route code already exists: " + route.getRouteCode());
        }

        // Check if bus is already assigned to another route
        if (isBusAssignedToRoute(route.getBusId())) {
            throw new RuntimeException("Bus is already assigned to another route: " + route.getBusId());
        }

        route.setIsActive(true);
        return routeRepository.save(route);
    }

    public Route updateRoute(String id, Route updatedRoute) {
        Optional<Route> existingRouteOpt = routeRepository.findById(id);
        if (existingRouteOpt.isPresent()) {
            Route existingRoute = existingRouteOpt.get();

            // Check if route code is being changed and if new code already exists
            if (!existingRoute.getRouteCode().equals(updatedRoute.getRouteCode()) &&
                    isRouteCodeExists(updatedRoute.getRouteCode())) {
                throw new RuntimeException("Route code already exists: " + updatedRoute.getRouteCode());
            }

            updatedRoute.setId(id);
            return routeRepository.save(updatedRoute);
        } else {
            throw new RuntimeException("Route not found with id: " + id);
        }
    }
}