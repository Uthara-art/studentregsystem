package com.example.studentreg.repository;

import com.example.studentreg.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    // ✅ Find registration by student and course
    Optional<Registration> findByStudentAndCourse(Student student, Course course);

    // ✅ Find all registrations of a student
    List<Registration> findByStudent(Student student);

    // ✅ Delete all registrations of a student
    void deleteByStudent(Student student);

    // ✅ Count total registrations by status (Approved, Pending, Rejected)
    long countByStatus(RegistrationStatus status);

    // ✅ Count registrations by course
    long countByCourse(Course course);

    // ✅ Count registrations by course and status
    long countByCourseAndStatus(Course course, RegistrationStatus status);
}
