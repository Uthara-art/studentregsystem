package com.example.studentreg.controller;

import com.example.studentreg.entity.Student;
import com.example.studentreg.service.DashboardService;
import com.example.studentreg.service.RegistrationService;
import com.example.studentreg.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final DashboardService dashboardService;
    private final RegistrationService registrationService;
    private final StudentService studentService;

    public AdminController(DashboardService dashboardService,
            RegistrationService registrationService,
            StudentService studentService) {
        this.dashboardService = dashboardService;
        this.registrationService = registrationService;
        this.studentService = studentService;
    }

    // ✅ Admin Login Page
    @GetMapping("/login")
    public String showLoginPage() {
        return "admin-login"; // Thymeleaf file: admin-login.html
    }

    // ✅ Admin Dashboard
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("totalStudents", dashboardService.getTotalStudents());
        model.addAttribute("totalCourses", dashboardService.getTotalCourses());
        model.addAttribute("totalRegistrations", dashboardService.getTotalRegistrations());
        return "admin-dashboard"; // Thymeleaf file: admin-dashboard.html
    }

    // ✅ View All Students
    @GetMapping("/students")
    public String viewStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "admin-students"; // Thymeleaf file: admin-students.html
    }

    // ✅ View a single student's details
    @GetMapping("/students/view/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
        Optional<Student> studentOpt = studentService.getStudentById(id);
        if (studentOpt.isEmpty()) {
            // student not found — redirect back to list (you can add a flash message if
            // desired)
            return "redirect:/admin/students";
        }
        model.addAttribute("student", studentOpt.get());
        return "admin-student-view"; // create templates/admin-student-view.html
    }

    // ✅ View All Registrations
    @GetMapping("/registrations")
    public String viewRegistrations(Model model) {
        model.addAttribute("registrations", registrationService.getAllRegistrations());
        return "admin-registrations"; // Thymeleaf file: admin-registrations.html
    }

    // Delete student (keeps existing behavior)
    @PostMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/admin/students";
    }

}
