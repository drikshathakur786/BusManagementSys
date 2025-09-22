package com.example.demo.model;

import lombok.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stop {

    @Positive
    private int sequence;                // order in route

    @NotBlank
    private String name;                 // stop name

    @NotNull
    private GeoJsonPoint location;       // exact stop coordinates

    private LocalDateTime arrivalTime;   // actual arrival for live tracking

    @Builder.Default
    private StopStatus status = StopStatus.PENDING;  // PENDING, DONE, SKIPPED
}