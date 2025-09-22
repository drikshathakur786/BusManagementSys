package com.example.demo.service;

import com.example.demo.model.Students;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Basic CRUD operations
    public List<Students> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Students> getStudentById(String id) {
        return studentRepository.findById(id);
    }

    public Students saveStudent(Students student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }

    // Enhanced service methods
    public List<Students> getStudentsByPaymentStatus(String status) {
        return studentRepository.findByPaymentStatus(status);
    }

    public List<Students> getStudentsByBus(String busNo) {
        return studentRepository.findByBusNo(busNo);
    }

    public Optional<Students> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public Optional<Students> getStudentByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    public List<Students> getStudentsByInstitution(String institutionName) {
        return studentRepository.findByInstitutionName(institutionName);
    }

    public List<Students> getStudentsByGrade(String grade) {
        return studentRepository.findByGrade(grade);
    }

    public List<Students> getStudentsByRoute(String routeId) {
        return studentRepository.findByRouteId(routeId);
    }

    public List<Students> getActiveStudents() {
        return studentRepository.findByIsActiveTrue();
    }

    public List<Students> getInactiveStudents() {
        return studentRepository.findByIsActiveFalse();
    }

    public List<Students> getStudentsWithPendingPayments() {
        return studentRepository.findStudentsWithPendingPayments();
    }

    public List<Students> getOverdueStudents() {
        return studentRepository.findByNextPaymentDueBefore(LocalDateTime.now());
    }

    public List<Students> searchStudentsByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Students> getStudentsByPickupLocation(String pickupLocation) {
        return studentRepository.findByPickupLocation(pickupLocation);
    }

    public List<Students> getStudentsByDropoffLocation(String dropoffLocation) {
        return studentRepository.findByDropoffLocation(dropoffLocation);
    }

    public List<Students> getStudentsByFeesRange(Double minAmount, Double maxAmount) {
        return studentRepository.findByFeesAmountBetween(minAmount, maxAmount);
    }

    public List<Students> getStudentsByInstitutionAndGrade(String institutionName, String grade) {
        return studentRepository.findByInstitutionNameAndGrade(institutionName, grade);
    }

    public List<Students> getStudentsByBusAndPaymentStatus(String busNo, String paymentStatus) {
        return studentRepository.findByBusNoAndPaymentStatus(busNo, paymentStatus);
    }

    // Utility methods
    public long getStudentCountByPaymentStatus(String paymentStatus) {
        return studentRepository.countByPaymentStatus(paymentStatus);
    }

    public long getStudentCountByBus(String busNo) {
        return studentRepository.countByBusNo(busNo);
    }

    public long getActiveStudentCount() {
        return studentRepository.countByIsActiveTrue();
    }

    public boolean isStudentIdExists(String studentId) {
        return studentRepository.existsByStudentId(studentId);
    }

    public boolean isEmailExists(String email) {
        return studentRepository.existsByEmail(email);
    }

    // Business logic methods
    public Students createStudent(Students student) {
        // Check if student ID already exists
        if (isStudentIdExists(student.getStudentId())) {
            throw new RuntimeException("Student ID already exists: " + student.getStudentId());
        }

        // Check if email already exists
        if (isEmailExists(student.getEmail())) {
            throw new RuntimeException("Email already exists: " + student.getEmail());
        }

        // Set default values
        student.setIsActive(true);
        if (student.getPaymentStatus() == null) {
            student.setPaymentStatus("PENDING");
        }

        return studentRepository.save(student);
    }

    public Students updateStudent(String id, Students updatedStudent) {
        Optional<Students> existingStudentOpt = studentRepository.findById(id);
        if (existingStudentOpt.isPresent()) {
            Students existingStudent = existingStudentOpt.get();

            // Check if student ID is being changed and if new ID already exists
            if (!existingStudent.getStudentId().equals(updatedStudent.getStudentId()) &&
                    isStudentIdExists(updatedStudent.getStudentId())) {
                throw new RuntimeException("Student ID already exists: " + updatedStudent.getStudentId());
            }

            // Check if email is being changed and if new email already exists
            if (!existingStudent.getEmail().equals(updatedStudent.getEmail()) &&
                    isEmailExists(updatedStudent.getEmail())) {
                throw new RuntimeException("Email already exists: " + updatedStudent.getEmail());
            }

            updatedStudent.setId(id);
            return studentRepository.save(updatedStudent);
        } else {
            throw new RuntimeException("Student not found with id: " + id);
        }
    }

    public void deactivateStudent(String id) {
        Optional<Students> studentOpt = studentRepository.findById(id);
        if (studentOpt.isPresent()) {
            Students student = studentOpt.get();
            student.setIsActive(false);
            studentRepository.save(student);
        } else {
            throw new RuntimeException("Student not found with id: " + id);
        }
    }

    public void activateStudent(String id) {
        Optional<Students> studentOpt = studentRepository.findById(id);
        if (studentOpt.isPresent()) {
            Students student = studentOpt.get();
            student.setIsActive(true);
            studentRepository.save(student);
        } else {
            throw new RuntimeException("Student not found with id: " + id);
        }
    }

    public void updatePaymentStatus(String id, String paymentStatus, String paymentId) {
        Optional<Students> studentOpt = studentRepository.findById(id);
        if (studentOpt.isPresent()) {
            Students student = studentOpt.get();
            student.setPaymentStatus(paymentStatus);
            student.setLastPaymentId(paymentId);
            student.setLastPaymentDate(LocalDateTime.now());

            // Set next payment due date (assuming monthly payments)
            if ("PAID".equals(paymentStatus)) {
                student.setNextPaymentDue(LocalDateTime.now().plusMonths(1));
            }

            studentRepository.save(student);
        } else {
            throw new RuntimeException("Student not found with id: " + id);
        }
    }

    public void assignBusToStudent(String studentId, String busNo, String routeId) {
        Optional<Students> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            Students student = studentOpt.get() ;
            student.setBusNo(busNo);
            student.setRouteId(routeId);
            studentRepository.save(student);
        } else {
            throw new RuntimeException("Student not found with id: " + studentId);
        }
    }
}