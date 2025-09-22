package com.example.demo.controller;

import com.example.demo.model.Students;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // -------------------- BASIC CRUD --------------------

    @GetMapping
    public ResponseEntity<List<Students>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Students> getStudentById(@PathVariable String id) {
        Optional<Students> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Students> createStudent(@RequestBody Students student) {
        try {
            Students savedStudent = studentService.createStudent(student);
            return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Students> updateStudent(@PathVariable String id, @RequestBody Students student) {
        try {
            Students updatedStudent = studentService.updateStudent(id, student);
            return ResponseEntity.ok(updatedStudent);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    // -------------------- BUSINESS LOGIC --------------------

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateStudent(@PathVariable String id) {
        studentService.deactivateStudent(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activateStudent(@PathVariable String id) {
        studentService.activateStudent(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/payment")
    public ResponseEntity<Void> updatePaymentStatus(
            @PathVariable String id,
            @RequestParam String paymentStatus,
            @RequestParam String paymentId) {
        studentService.updatePaymentStatus(id, paymentStatus, paymentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/assign-bus")
    public ResponseEntity<Void> assignBusToStudent(
            @PathVariable String id,
            @RequestParam String busNo,
            @RequestParam String routeId) {
        studentService.assignBusToStudent(id, busNo, routeId);
        return ResponseEntity.ok().build();
    }

    // -------------------- SEARCH & FILTER --------------------

    @GetMapping("/search")
    public ResponseEntity<List<Students>> searchStudentsByName(@RequestParam String name) {
        return ResponseEntity.ok(studentService.searchStudentsByName(name));
    }

    @GetMapping("/bus/{busNo}")
    public ResponseEntity<List<Students>> getStudentsByBus(@PathVariable String busNo) {
        return ResponseEntity.ok(studentService.getStudentsByBus(busNo));
    }

    @GetMapping("/route/{routeId}")
    public ResponseEntity<List<Students>> getStudentsByRoute(@PathVariable String routeId) {
        return ResponseEntity.ok(studentService.getStudentsByRoute(routeId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Students>> getActiveStudents() {
        return ResponseEntity.ok(studentService.getActiveStudents());
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<Students>> getInactiveStudents() {
        return ResponseEntity.ok(studentService.getInactiveStudents());
    }

    @GetMapping("/payments/pending")
    public ResponseEntity<List<Students>> getStudentsWithPendingPayments() {
        return ResponseEntity.ok(studentService.getStudentsWithPendingPayments());
    }

    @GetMapping("/payments/overdue")
    public ResponseEntity<List<Students>> getOverdueStudents() {
        return ResponseEntity.ok(studentService.getOverdueStudents());
    }

    @GetMapping("/pickup/{pickupLocation}")
    public ResponseEntity<List<Students>> getStudentsByPickupLocation(@PathVariable String pickupLocation) {
        return ResponseEntity.ok(studentService.getStudentsByPickupLocation(pickupLocation));
    }

    @GetMapping("/dropoff/{dropoffLocation}")
    public ResponseEntity<List<Students>> getStudentsByDropoffLocation(@PathVariable String dropoffLocation) {
        return ResponseEntity.ok(studentService.getStudentsByDropoffLocation(dropoffLocation));
    }

    @GetMapping("/fees-range")
    public ResponseEntity<List<Students>> getStudentsByFeesRange(
            @RequestParam Double min,
            @RequestParam Double max) {
        return ResponseEntity.ok(studentService.getStudentsByFeesRange(min, max));
    }

    @GetMapping("/institution-grade")
    public ResponseEntity<List<Students>> getStudentsByInstitutionAndGrade(
            @RequestParam String institutionName,
            @RequestParam String grade) {
        return ResponseEntity.ok(studentService.getStudentsByInstitutionAndGrade(institutionName, grade));
    }

    @GetMapping("/bus-payment")
    public ResponseEntity<List<Students>> getStudentsByBusAndPaymentStatus(
            @RequestParam String busNo,
            @RequestParam String paymentStatus) {
        return ResponseEntity.ok(studentService.getStudentsByBusAndPaymentStatus(busNo, paymentStatus));
    }

    // -------------------- UTILITY --------------------

    @GetMapping("/count/payment-status/{status}")
    public ResponseEntity<Long> getStudentCountByPaymentStatus(@PathVariable String status) {
        return ResponseEntity.ok(studentService.getStudentCountByPaymentStatus(status));
    }

    @GetMapping("/count/bus/{busNo}")
    public ResponseEntity<Long> getStudentCountByBus(@PathVariable String busNo) {
        return ResponseEntity.ok(studentService.getStudentCountByBus(busNo));
    }

    @GetMapping("/count/active")
    public ResponseEntity<Long> getActiveStudentCount() {
        return ResponseEntity.ok(studentService.getActiveStudentCount());
    }
}
