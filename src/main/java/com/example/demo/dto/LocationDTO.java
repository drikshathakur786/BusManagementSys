package com.example.demo.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDTO {
    private String busNo;
    private double latitude;
    private double longitude;
    private LocalDateTime lastUpdated;
}
