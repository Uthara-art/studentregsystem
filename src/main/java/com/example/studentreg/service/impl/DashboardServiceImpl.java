package com.example.studentreg.service.impl;

import com.example.studentreg.entity.Course;
import com.example.studentreg.repository.CourseRepository;
import com.example.studentreg.repository.RegistrationRepository;
import com.example.studentreg.repository.StudentRepository; // 🌟 IMPORT MISSING REPOSITORY
import com.example.studentreg.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;
    private final StudentRepository studentRepository; // 🌟 NEW: Field for StudentRepository

    // 🌟 Inject all required repositories using a constructor for better practice
    @Autowired
    public DashboardServiceImpl(CourseRepository courseRepository,
            RegistrationRepository registrationRepository,
            StudentRepository studentRepository) { // 🌟 Inject StudentRepository
        this.courseRepository = courseRepository;
        this.registrationRepository = registrationRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        // Use the implemented methods
        long totalCourses = getTotalCourses(); // Use the corrected method
        long totalRegistrations = getTotalRegistrations();
        long totalStudents = getTotalStudents(); // Use the corrected method

        stats.put("totalCourses", totalCourses);
        stats.put("totalRegistrations", totalRegistrations);
        stats.put("totalStudents", totalStudents); // Add total students to the map
        stats.put("availableSeats", getAvailableSeats());

        return stats;
    }

    @Override
    public long getTotalRegistrations() {
        return registrationRepository.count();
    }

    @Override
    public int getAvailableSeats() {
        List<Course> courses = courseRepository.findAll();
        int totalSeats = 0;
        int usedSeats = 0;

        for (Course course : courses) {
            totalSeats += course.getCapacity();
            // Assuming you have this method in RegistrationRepository:
            // long countByCourse(Course course);
            usedSeats += registrationRepository.countByCourse(course);
        }

        return totalSeats - usedSeats;
    }

    // 🌟 FIX 1: Implement getTotalStudents() correctly
    @Override
    public long getTotalStudents() {
        // Use the injected StudentRepository to count all Student entities
        return studentRepository.count();
    }

    // 🌟 FIX 2: Implement getTotalCourses() correctly
    @Override
    public long getTotalCourses() {
        // Use the injected CourseRepository to count all Course entities
        return courseRepository.count();
    }
}