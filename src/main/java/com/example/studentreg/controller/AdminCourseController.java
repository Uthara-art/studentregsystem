package com.example.studentreg.controller;

import com.example.studentreg.entity.Course;
import com.example.studentreg.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/courses")
public class AdminCourseController {

    private final CourseService courseService;

    public AdminCourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Show all courses
    @GetMapping
    public String viewCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("course", new Course()); // for form
        return "admin-courses"; // ✅ should match your HTML filename
    }

    // Handle add new course
    @PostMapping("/add")
    public String addCourse(@ModelAttribute Course course, Model model) {
        try {
            courseService.saveCourse(course);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error adding course: " + e.getMessage());
            return "admin-courses";
        }
        return "redirect:/admin/courses";
    }

    // Optional: delete a course
    @PostMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/admin/courses";
    }
}
