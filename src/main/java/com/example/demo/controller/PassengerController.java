package com.example.demo.controller;

import com.example.demo.model.Passangers;
import com.example.demo.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/passengers")
@CrossOrigin(origins = "*")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @GetMapping
    public ResponseEntity<List<Passangers>> getAllPassengers() {
        try {
            List<Passangers> passengers = passengerService.getAllPassengers();
            return new ResponseEntity<>(passengers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passangers> getPassengerById(@PathVariable String id) {
        try {
            Optional<Passangers> passenger = passengerService.getPassengerById(id);
            if (passenger.isPresent()) {
                return new ResponseEntity<>(passenger.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Passangers> getPassengerByEmail(@PathVariable String email) {
        try {
            Optional<Passangers> passenger = passengerService.getPassengerByEmail(email);
            if (passenger.isPresent()) {
                return new ResponseEntity<>(passenger.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Passangers> createPassenger(@RequestBody Passangers passenger) {
        try {
            Passangers savedPassenger = passengerService.savePassenger(passenger);
            return new ResponseEntity<>(savedPassenger, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Passangers> updatePassenger(@PathVariable String id, @RequestBody Passangers passenger) {
//        try {
//            Optional<Passangers> existingPassenger = passengerService.getPassengerById(id);
//            if (existingPassenger.isPresent()) {
//                passenger.setId(id);
//                Passangers updatedPassenger = passengerService.savePassenger(passenger);
//                return new ResponseEntity<>(updatedPassenger, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePassenger(@PathVariable String id) {
        try {
            Optional<Passangers> passenger = passengerService.getPassengerById(id);
            if (passenger.isPresent()) {
                passengerService.deletePassenger(id);
                return new ResponseEntity<>("Passenger deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Passenger not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting passenger: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
