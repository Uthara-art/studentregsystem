package com.example.studentreg.service.impl;

import com.example.studentreg.entity.Course;
import com.example.studentreg.entity.Registration;
import com.example.studentreg.entity.Student;
import com.example.studentreg.repository.CourseRepository;
import com.example.studentreg.repository.RegistrationRepository;
import com.example.studentreg.repository.StudentRepository;
import com.example.studentreg.service.RegistrationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public RegistrationServiceImpl(RegistrationRepository registrationRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository) {
        this.registrationRepository = registrationRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public Registration registerStudentToCourse(Long studentId, Long courseId) throws IllegalStateException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course not found"));

        // check duplicate
        Optional<Registration> exists = registrationRepository.findByStudentAndCourse(student, course);
        if (exists.isPresent()) {
            throw new IllegalStateException("Student already registered in this course");
        }

        // check capacity: use repository count to avoid relying only on course.enrolled
        // field
        long enrolled = registrationRepository.countByCourse(course);
        if (course.getCapacity() <= enrolled) {
            throw new IllegalStateException("Course capacity full");
        }

        // safer creation using setters (avoid nullability problems)
        Registration reg = new Registration();
        reg.setStudent(student);
        reg.setCourse(course);
        reg.setRegisteredAt(java.time.LocalDateTime.now());
        reg.setStatus("APPROVED");

        Registration saved = registrationRepository.save(reg);

        // Optionally, update course.enrolled if you track that field on Course
        // (If you maintain the enrolled field, update it here.)
        if (course.getEnrolled() < course.getCapacity()) {
            course.setEnrolled(course.getEnrolled() + 1);
            courseRepository.save(course);
        }

        return saved;
    }

    @Override
    @Transactional
    public void dropRegistration(Long studentId, Long courseId) throws IllegalStateException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course not found"));

        Optional<Registration> regOpt = registrationRepository.findByStudentAndCourse(student, course);
        if (regOpt.isEmpty()) {
            throw new IllegalStateException("Registration not found");
        }

        registrationRepository.delete(regOpt.get());

        // Optionally decrement enrolled count on course
        if (course.getEnrolled() > 0) {
            course.setEnrolled(course.getEnrolled() - 1);
            courseRepository.save(course);
        }
    }

    @Override
    public boolean isStudentRegisteredInCourse(Student student, Course course) {
        return registrationRepository.findByStudentAndCourse(student, course).isPresent();
    }

    @Override
    public List<Registration> getRegistrationsForStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null)
            return List.of();
        return registrationRepository.findByStudent(student);
    }

    @Override
    public long countEnrolledInCourse(Course course) {
        return registrationRepository.countByCourse(course);
    }

    @Override
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }
}
