package com.example.demo.controller;

import com.example.demo.dto.LocationDTO;
import com.example.demo.mapper.LocationMapper;
import com.example.demo.model.Location;
import com.example.demo.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
@CrossOrigin(origins = "*")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // ✅ for WebSocket broadcasting

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        try {
            List<Location> locations = locationService.getAllLocations();
            return new ResponseEntity<>(locations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bus/{busNo}")
    public ResponseEntity<Location> getLocationByBusNo(@PathVariable String busNo) {
        try {
            Optional<Location> location = locationService.getLocationByBusNo(busNo);
            return location.map(value ->
                            new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        try {
            Location savedLocation = locationService.saveLocation(location);

            // ✅ Broadcast to WebSocket subscribers
            messagingTemplate.convertAndSend(
                    "/topic/bus/" + savedLocation.getBusNo() + "/location",
                    savedLocation
            );

            return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/bus/{busNo}")
    public ResponseEntity<Location> updateLocationByBusNo(@PathVariable String busNo, @RequestBody Location location) {
        try {
            Optional<Location> existingLocation = locationService.getLocationByBusNo(busNo);
            if (existingLocation.isPresent()) {
                location.setId(existingLocation.get().getId());
                location.setBusNo(busNo);
                Location updatedLocation = locationService.saveLocation(location);

                // ✅ Broadcast updated location
                messagingTemplate.convertAndSend(
                        "/topic/bus/" + busNo + "/location",
                        updatedLocation
                );

                return new ResponseEntity<>(updatedLocation, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
