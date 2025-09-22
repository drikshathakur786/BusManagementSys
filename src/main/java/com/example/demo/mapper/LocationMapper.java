package com.example.demo.mapper;

import com.example.demo.dto.LocationDTO;
import com.example.demo.model.Location;

import java.time.LocalDateTime;

public class LocationMapper {

    public static LocationDTO toDTO(Location location) {
        return LocationDTO.builder()
                .busNo(location.getBusNo())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .lastUpdated(LocalDateTime.parse(location.getLastUpdated()))
                .build();
    }

    public static Location toEntity(LocationDTO dto) {
        Location location = new Location();
        location.setBusNo(dto.getBusNo());
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());
        location.setLastUpdated(String.valueOf(dto.getLastUpdated()));
        return location;
    }
}
