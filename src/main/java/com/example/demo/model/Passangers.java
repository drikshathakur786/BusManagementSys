package com.example.demo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passangers {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @Indexed(unique = true)
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    private String address;

    private String busNo; // Which bus the passenger is assigned to

    private String routeId; // Which route the passenger takes

    private String pickupLocation;
    private String dropoffLocation;
}