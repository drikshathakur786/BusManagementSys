package com.example.demo.repository;

import com.example.demo.model.Passangers;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface PassengerRepository extends MongoRepository<Passangers, String> {

    Optional<Passangers> findByEmail(String email);

    Optional<Passangers> findByBusNo(String busNo);
}