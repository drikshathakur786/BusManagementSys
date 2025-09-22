package com.example.demo.model;

import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="routes")
public class Route {
    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank(message = "Route code is required")
    private String routeCode;

    @NotBlank(message = "Route name is required")
    private String routeName;

    @NotBlank(message = "Start location is required")
    private String startLocation;

    @NotBlank(message = "End location is required")
    private String endLocation;

    @NotNull(message = "Bus ID is required")
    private String busId;

    @Size(min = 2, message = "Route must have at least 2 stops")
    private List<String> stops;

    private LocalTime departureTime;
    private LocalTime arrivalTime;

    private Double distance; // in kilometers
    private Double fare; // ticket price

    private Boolean isActive = true;

    // Custom constructor without id (for creating new routes)
    public Route(String routeCode, String routeName, String startLocation,
                 String endLocation, String busId, List<String> stops,
                 LocalTime departureTime, LocalTime arrivalTime,
                 Double distance, Double fare) {
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.busId = busId;
        this.stops = stops;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.distance = distance;
        this.fare = fare;
        this.isActive = true;
    }
}