package com.example.studentreg.service.impl;

import com.example.studentreg.entity.Course;
import com.example.studentreg.repository.CourseRepository;
import com.example.studentreg.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course saveCourse(Course course) {
        // simple sanity: ensure capacity is not negative
        if (course.getCapacity() < 0) {
            throw new IllegalArgumentException("Capacity must be 0 or greater");
        }
        // reset enrolled if null/negative (defensive)
        if (course.getEnrolled() < 0) {
            course.setEnrolled(0);
        }
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course updated) throws IllegalArgumentException {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + id));

        if (updated.getName() != null)
            existing.setName(updated.getName());
        if (updated.getDescription() != null)
            existing.setDescription(updated.getDescription());

        // capacity change: do not allow capacity less than already enrolled students
        if (updated.getCapacity() >= 0) {
            if (updated.getCapacity() < existing.getEnrolled()) {
                throw new IllegalArgumentException("New capacity cannot be less than currently enrolled students");
            }
            existing.setCapacity(updated.getCapacity());
        }

        // allow manually adjusting enrolled only if value valid (usually managed
        // elsewhere)
        if (updated.getEnrolled() >= 0) {
            existing.setEnrolled(updated.getEnrolled());
        }

        return courseRepository.save(existing);
    }

    @Override
    public void deleteCourse(Long id) {
        // You might want to check registrations before deleting in a real app.
        courseRepository.deleteById(id);
    }
}
