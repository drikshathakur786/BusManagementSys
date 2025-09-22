package com.example.demo.service;

import com.example.demo.model.Location;
import com.example.demo.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
//    public Optional<Location> getLocationByBusNo(String busNo) {
//        return locationRepository.findByBusNo(busNo);
//    }
    public  Optional<Location> getLocationByBusNo(String busNo) {
        if (busNo == null || busNo.isBlank()) {
            System.out.println("null");
            return Optional.empty();

        }

        System.out.println(locationRepository.findByBusNo(busNo));

        return locationRepository.findByBusNo(busNo);
    }

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }
}
