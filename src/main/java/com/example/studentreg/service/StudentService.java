package com.example.studentreg.service;

import com.example.studentreg.entity.Student;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student saveStudent(Student student);

    List<Student> getAllStudents();

    void deleteStudent(Long id);

    Optional<Student> findByEmail(String email);

    // 🔄 Rename to match controller
    Optional<Student> getStudentById(Long id);
}
