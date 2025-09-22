package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payments")
public class Payment {
    @Id
    private String id;

    private String studentId;       // reference to Student
    private double amount;          // amount paid
    private String paymentMethod;   // "UPI", "CARD", "NETBANKING"
    private String transactionId;   // from gateway (e.g., Razorpay, Stripe)
    private String status;          // "SUCCESS", "FAILED", "PENDING"

    private LocalDateTime createdAt;
}
