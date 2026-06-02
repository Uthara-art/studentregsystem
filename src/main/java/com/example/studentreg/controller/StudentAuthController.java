package com.example.studentreg.controller;

import com.example.studentreg.entity.Student;
import com.example.studentreg.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentAuthController {

    private final StudentService studentService;

    public StudentAuthController(StudentService studentService) {
        this.studentService = studentService;
    }

    /** 🔹 Show the student signup form */
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("student", new Student());
        return "student-signup"; // view name → templates/student-signup.html
    }

    /** 🔹 Handle student signup (create new account) */
    @PostMapping("/signup")
    public String registerStudent(@ModelAttribute("student") Student student, Model model) {
        Optional<Student> existingStudent = studentService.findByEmail(student.getEmail());

        if (existingStudent.isPresent()) {
            model.addAttribute("error", "Email is already registered. Please use another or log in.");
            return "student-signup";
        }

        studentService.saveStudent(student);
        model.addAttribute("success", "Registration successful! You can now log in.");
        return "redirect:/student/login";
    }

    /** 🔹 Show the student login form */
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("student", new Student());
        return "student-login"; // view name → templates/student-login.html
    }

    /** 🔹 Handle login form submission */
    @PostMapping("/login")
    public String loginStudent(@RequestParam String email,
            @RequestParam String password,
            Model model) {
        Optional<Student> studentOpt = studentService.findByEmail(email);

        if (studentOpt.isEmpty()) {
            model.addAttribute("error", "No student found with that email.");
            return "student-login";
        }

        Student student = studentOpt.get();
        if (!student.getPassword().equals(password)) {
            model.addAttribute("error", "Incorrect password. Please try again.");
            return "student-login";
        }

        // ✅ Success: redirect to student dashboard
        return "redirect:/student/dashboard?studentId=" + student.getId();
    }
}
