package com.example.demo.repository;

import com.example.demo.model.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TeacherRepository extends MongoRepository<Teacher, String> {
    List<Teacher> findByBusNo(String busNo);
    Teacher findByEmail(String email);
}
