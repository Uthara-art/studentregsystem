package com.example.studentreg.service;

import com.example.studentreg.entity.Course;
import com.example.studentreg.entity.Registration;
import com.example.studentreg.entity.Student;

import java.util.List;

public interface RegistrationService {
    Registration registerStudentToCourse(Long studentId, Long courseId) throws IllegalStateException;

    void dropRegistration(Long studentId, Long courseId) throws IllegalStateException;

    boolean isStudentRegisteredInCourse(Student student, Course course);

    List<Registration> getRegistrationsForStudent(Long studentId);

    long countEnrolledInCourse(Course course);

    List<Registration> getAllRegistrations();
}
