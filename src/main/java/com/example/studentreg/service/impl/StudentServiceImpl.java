package com.example.studentreg.service.impl;

import com.example.studentreg.entity.Student;
import com.example.studentreg.repository.StudentRepository;
import com.example.studentreg.service.StudentService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    // ✅ Implemented getStudentById
    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
}
