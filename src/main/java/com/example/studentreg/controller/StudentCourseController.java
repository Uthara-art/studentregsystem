package com.example.studentreg.controller;

import com.example.studentreg.entity.Course;
import com.example.studentreg.entity.Registration;
import com.example.studentreg.entity.Student;
import com.example.studentreg.service.CourseService;
import com.example.studentreg.service.RegistrationService;
import com.example.studentreg.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
public class StudentCourseController {

    private final CourseService courseService;
    private final StudentService studentService;
    private final RegistrationService registrationService;

    public StudentCourseController(CourseService courseService,
            StudentService studentService,
            RegistrationService registrationService) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.registrationService = registrationService;
    }

    /**
     * Show all courses for a student (catalog). Accepts studentId (from login
     * redirect or session).
     * URL example: /student/courses?studentId=1
     */
    @GetMapping("/courses")
    public String showCoursesForStudent(@RequestParam("studentId") Long studentId, Model model) {
        Student student = studentService.getStudentById(studentId).orElse(null);
        if (student == null) {
            model.addAttribute("error", "Student not found.");
            return "student-login";
        }

        List<Course> courses = courseService.getAllCourses();
        List<Registration> regs = registrationService.getRegistrationsForStudent(studentId);

        // quick set of course ids the student already registered for
        Set<Long> registeredCourseIds = regs.stream()
                .map(r -> r.getCourse().getId())
                .collect(Collectors.toSet());

        model.addAttribute("student", student);
        model.addAttribute("courses", courses);
        model.addAttribute("registeredCourseIds", registeredCourseIds);
        model.addAttribute("registrations", regs);

        return "student-courses";
    }

    /**
     * Register current student to a course (form POST).
     * Expects studentId and courseId form fields.
     */
    @PostMapping("/courses/register")
    public String registerToCourse(@RequestParam("studentId") Long studentId,
            @RequestParam("courseId") Long courseId,
            Model model) {
        // optional: check student exists
        if (studentService.getStudentById(studentId).isEmpty()) {
            model.addAttribute("error", "Student not found");
            return "redirect:/student/login";
        }

        try {
            registrationService.registerStudentToCourse(studentId, courseId);
        } catch (IllegalStateException ex) {
            // registrationService should throw meaningful exceptions (e.g. "Course full" or
            // "Already registered")
            model.addAttribute("error", ex.getMessage());
            // keep redirect so flash message pattern can be added later — for now just
            // redirecting back
        }

        return "redirect:/student/courses?studentId=" + studentId;
    }
}
