package com.example.studentreg.controller;

import com.example.studentreg.entity.Course;
import com.example.studentreg.entity.Registration;
import com.example.studentreg.entity.Student;
import com.example.studentreg.service.CourseService;
import com.example.studentreg.service.RegistrationService;
import com.example.studentreg.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/students/{studentId}/courses")
public class RegistrationController {

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    private final StudentService studentService;
    private final CourseService courseService;
    private final RegistrationService registrationService;

    public RegistrationController(StudentService studentService,
            CourseService courseService,
            RegistrationService registrationService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.registrationService = registrationService;
    }

    /**
     * Show all courses and mark which ones the student is registered in.
     */
    @GetMapping
    public String showCoursesForStudent(@PathVariable("studentId") Long studentId, Model model) {
        Student student = studentService.getStudentById(studentId).orElse(null);
        if (student == null) {
            model.addAttribute("errorMessage", "Student not found");
            return "admin-student-courses";
        }
        List<Course> courses = courseService.getAllCourses();
        List<Registration> regs = registrationService.getRegistrationsForStudent(studentId);

        model.addAttribute("student", student);
        model.addAttribute("courses", courses);
        model.addAttribute("registrations", regs);

        return "admin-student-courses";
    }

    /**
     * Register the student into a course (POST).
     */
    @PostMapping("/register")
    public String register(@PathVariable("studentId") Long studentId,
            @RequestParam("courseId") Long courseId,
            Model model) {
        try {
            registrationService.registerStudentToCourse(studentId, courseId);
        } catch (IllegalStateException ex) {
            log.warn("register error: {}", ex.getMessage());
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/admin/students/" + studentId + "/courses";
    }

    /**
     * Drop registration (POST).
     */
    @PostMapping("/drop")
    public String drop(@PathVariable("studentId") Long studentId,
            @RequestParam("courseId") Long courseId,
            Model model) {
        try {
            registrationService.dropRegistration(studentId, courseId);
        } catch (IllegalStateException ex) {
            log.warn("drop error: {}", ex.getMessage());
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/admin/students/" + studentId + "/courses";
    }
}
