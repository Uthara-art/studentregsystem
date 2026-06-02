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
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentDashboardController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final RegistrationService registrationService;

    public StudentDashboardController(StudentService studentService,
            CourseService courseService,
            RegistrationService registrationService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.registrationService = registrationService;
    }

    // 🔹 Student Dashboard (show available & registered courses)
    @GetMapping("/dashboard")
    public String showDashboard(@RequestParam("studentId") Long studentId, Model model) {
        Optional<Student> studentOpt = studentService.getStudentById(studentId);

        if (studentOpt.isEmpty()) {
            model.addAttribute("errorMessage", "Student not found!");
            return "error"; // ✅ Use a proper error page instead of dashboard
        }

        Student student = studentOpt.get();
        List<Course> allCourses = courseService.getAllCourses();
        List<Registration> registrations = registrationService.getRegistrationsForStudent(studentId);

        // ✅ Pass data to template
        model.addAttribute("student", student);
        model.addAttribute("allCourses", allCourses);
        model.addAttribute("registrations", registrations);

        return "student-dashboard";
    }

    // 🔹 Register for a course
    @PostMapping("/course/register")
    public String registerCourse(@RequestParam("studentId") Long studentId,
            @RequestParam("courseId") Long courseId,
            Model model) {
        try {
            registrationService.registerStudentToCourse(studentId, courseId);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/student/dashboard?studentId=" + studentId;
    }

    // 🔹 Drop a course
    @PostMapping("/course/drop")
    public String dropCourse(@RequestParam("studentId") Long studentId,
            @RequestParam("courseId") Long courseId,
            Model model) {
        try {
            registrationService.dropRegistration(studentId, courseId);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/student/dashboard?studentId=" + studentId;
    }
}
