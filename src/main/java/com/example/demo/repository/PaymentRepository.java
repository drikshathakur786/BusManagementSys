package com.example.demo.repository;

import com.example.demo.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PaymentRepository extends MongoRepository<Payment, String> {

    List<Payment> findByStudentId(String studentId);

    List<Payment> findByStatus(String status);
}