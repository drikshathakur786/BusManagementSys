package com.example.demo.controller;

import com.example.demo.model.Buses;
import com.example.demo.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DuplicateKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/buses")
@CrossOrigin(origins = "*")
public class BusController {

    private static final Logger logger = LoggerFactory.getLogger(BusController.class);

    @Autowired
    private BusService busService;

    @GetMapping
    public ResponseEntity<List<Buses>> getAllBuses() {
        try {
            List<Buses> buses = busService.getAllBuses();
            logger.info("Retrieved {} buses", buses.size());
            return new ResponseEntity<>(buses, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving all buses: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Buses> getBusById(@PathVariable String id) {
        try {
            logger.info("Searching for bus with ID: {}", id);
            Optional<Buses> bus = busService.getBusById(id);
            if (bus.isPresent()) {
                logger.info("Found bus with ID: {}", id);
                return new ResponseEntity<>(bus.get(), HttpStatus.OK);
            } else {
                logger.warn("Bus not found with ID: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error retrieving bus with ID {}: ", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/busNo/{busNo}")
    public ResponseEntity<Buses> getBusByBusNo(@PathVariable String busNo) {
        try {
            logger.info("Searching for bus with busNo: {}", busNo);
            Buses bus = busService.getBusByBusNo(busNo);
            if (bus != null) {
                logger.info("Found bus with busNo: {}", busNo);
                return new ResponseEntity<>(bus, HttpStatus.OK);
            } else {
                logger.warn("Bus not found with busNo: {}", busNo);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error retrieving bus with busNo {}: ", busNo, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> createBus(@RequestBody Buses bus) {
        try {
            Buses savedBus = busService.saveBus(bus);
            logger.info("Created bus with ID: {}", savedBus.getId());
            return new ResponseEntity<>(savedBus, HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            logger.error("Duplicate key error creating bus: ", e);
            return new ResponseEntity<>("Bus number or plate already exists", HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.error("Error creating bus: ", e);
            return new ResponseEntity<>("Error creating bus: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBus(@PathVariable String id, @RequestBody Buses bus) {
        try {
            Optional<Buses> existingBus = busService.getBusById(id);
            if (existingBus.isPresent()) {
                bus.setId(id);
                Buses updatedBus = busService.saveBus(bus);
                logger.info("Updated bus with ID: {}", id);
                return new ResponseEntity<>(updatedBus, HttpStatus.OK);
            } else {
                logger.warn("Bus not found for update with ID: {}", id);
                return new ResponseEntity<>("Bus not found", HttpStatus.NOT_FOUND);
            }
        } catch (DuplicateKeyException e) {
            logger.error("Duplicate key error updating bus: ", e);
            return new ResponseEntity<>("Bus number or plate already exists", HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.error("Error updating bus with ID {}: ", id, e);
            return new ResponseEntity<>("Error updating bus: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBus(@PathVariable String id) {
        try {
            Optional<Buses> bus = busService.getBusById(id);
            if (bus.isPresent()) {
                busService.deleteBus(id);
                logger.info("Deleted bus with ID: {}", id);
                return new ResponseEntity<>("Bus deleted successfully", HttpStatus.OK);
            } else {
                logger.warn("Bus not found for deletion with ID: {}", id);
                return new ResponseEntity<>("Bus not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error deleting bus with ID {}: ", id, e);
            return new ResponseEntity<>("Error deleting bus: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}