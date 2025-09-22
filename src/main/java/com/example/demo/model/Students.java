package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "students")
public class Students extends Passangers {

    @Id
    private String id;

    @NotBlank(message = "Student ID is required")
    @Indexed(unique = true)
    private String studentId; // Roll number or student ID

    @NotBlank(message = "Institution name is required")
    private String institutionName; // School/College name

    private String grade; // Class/Grade/Year

    @NotBlank(message = "Payment status is required")
    private String paymentStatus; // PAID, PENDING, OVERDUE

    private String lastPaymentId; // Reference to latest Payment document

    @PositiveOrZero(message = "Fees amount must be positive or zero")
    private Double feesAmount;

    private LocalDateTime lastPaymentDate;

    private LocalDateTime nextPaymentDue;

    private Boolean isActive = true; // Student active status

    // Constructor without id (for creating new students)
    public Students(String name, String email, String phoneNumber, String address,
                    String busId, String routeId, String pickupLocation, String dropoffLocation,
                    String studentId, String institutionName, String grade,
                    String paymentStatus, Double feesAmount) {
        super(name, email, phoneNumber, address, busId, routeId, pickupLocation, dropoffLocation);
        this.studentId = studentId;
        this.institutionName = institutionName;
        this.grade = grade;
        this.paymentStatus = paymentStatus;
        this.feesAmount = feesAmount;
        this.isActive = true;
    }
}