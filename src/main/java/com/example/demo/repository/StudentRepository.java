package com.example.demo.repository;

import com.example.demo.model.Students;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Students, String> {

    // Find all students in a specific bus
    List<Students> findByBusNo(String busNo);

    // Find student by email (for login/authentication)
    Optional<Students> findByEmail(String email);

    // Find student by student ID
    Optional<Students> findByStudentId(String studentId);

    // Find students by payment status
//    List<Students> findByPaymentStatus(String paymentStatus) ;


    // Find students by institution
    List<Students> findByInstitutionName(String institutionName);

    // Find students by grade
    List<Students> findByGrade(String grade);

    // Find students by route
    List<Students> findByRouteId(String routeId);

    // Find active students only
    List<Students> findByIsActiveTrue();

    // Find inactive students
    List<Students> findByIsActiveFalse();

    // Find students with overdue payments
    List<Students> findByPaymentStatus(String status);

    // Find students whose payment is due before a certain date
    List<Students> findByNextPaymentDueBefore(LocalDateTime date);

    // Find students by pickup location
    List<Students> findByPickupLocation(String pickupLocation);

    // Find students by dropoff location
    List<Students> findByDropoffLocation(String dropoffLocation);

    // Find students with fees amount greater than specified amount
    List<Students> findByFeesAmountGreaterThan(Double amount);

    // Find students with fees amount between range
    List<Students> findByFeesAmountBetween(Double minAmount, Double maxAmount);

    // Custom query to find students with pending payments and overdue
    @Query("{'paymentStatus': {$in: ['PENDING', 'OVERDUE']}, 'isActive': true}")
    List<Students> findStudentsWithPendingPayments();

    // Find students by name (case-insensitive search)
    @Query("{'name': {$regex: ?0, $options: 'i'}}")
    List<Students> findByNameContainingIgnoreCase(String name);

    // Count students by payment status
    long countByPaymentStatus(String paymentStatus);

    // Count students by bus
    long countByBusNo(String busNo);

    // Count active students
    long countByIsActiveTrue();

    // Check if student ID exists
    boolean existsByStudentId(String studentId);

    // Check if email exists
    boolean existsByEmail(String email);

    // Find students by institution and grade
    List<Students> findByInstitutionNameAndGrade(String institutionName, String grade);

    // Find students by bus and payment status
    List<Students> findByBusNoAndPaymentStatus(String busNo, String paymentStatus);
}