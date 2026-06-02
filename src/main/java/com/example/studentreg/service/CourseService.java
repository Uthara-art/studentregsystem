package com.example.studentreg.service;

import com.example.studentreg.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> getAllCourses();

    Optional<Course> getCourseById(Long id);

    Course saveCourse(Course course);

    Course updateCourse(Long id, Course updated) throws IllegalArgumentException;

    void deleteCourse(Long id);
}
