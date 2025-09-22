package com.example.demo.repository;

import com.example.demo.model.Buses;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRepository extends MongoRepository<Buses, String> {

    // Find by bus number - returns single result
    Buses findByBusNo(String busNo);

    // Find by bus number - returns Optional (safer approach)
    Optional<Buses> findOptionalByBusNo(String busNo);

    // Find by bus plate
    Optional<Buses> findByBusPlate(String busPlate);

    // Check if bus number exists
    boolean existsByBusNo(String busNo);

    // Check if bus plate exists
    boolean existsByBusPlate(String busPlate);

    // Custom query to find by bus number (case insensitive)
    @Query("{'busNo': {$regex: ?0, $options: 'i'}}")
    Optional<Buses> findByBusNoIgnoreCase(String busNo);

    // Find by driver name
    Optional<Buses> findByDriverName(String driverName);
}