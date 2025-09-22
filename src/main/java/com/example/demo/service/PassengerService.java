package com.example.demo.service;

import com.example.demo.model.Passangers;
import com.example.demo.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    public List<Passangers> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Optional<Passangers> getPassengerById(String id) {
        return passengerRepository.findById(id);
    }

    public Passangers savePassenger(Passangers passenger) {
        return passengerRepository.save(passenger);
    }

    public void deletePassenger(String id) {
        passengerRepository.deleteById(id);
    }

    public Optional<Passangers> getPassengerByEmail(String email) {
        return passengerRepository.findByEmail(email);
    }
    // Add to PassengerService
    public Passangers updatePassenger(String id, Passangers updatedPassenger) {
        Optional<Passangers> existingOpt = passengerRepository.findById(id);
        if (existingOpt.isPresent()) {
            Passangers existing = existingOpt.get();
            // Update only the fields you want to change
            existing.setName(updatedPassenger.getName());
            existing.setEmail(updatedPassenger.getEmail());
            // ... update other fields
            return passengerRepository.save(existing);
        } else {
            throw new RuntimeException("Passenger not found with id: " + id);
        }
    }
}
