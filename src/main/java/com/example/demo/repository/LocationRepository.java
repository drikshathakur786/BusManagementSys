package com.example.demo.repository;

import com.example.demo.model.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.List;

public interface LocationRepository extends MongoRepository<Location, String> {

    Optional<Location> findByBusNo(String busNo);




        List<Location> findByLatitudeBetweenAndLongitudeBetween(
                double minLat, double maxLat,
                double minLon, double maxLon
        );
    }


