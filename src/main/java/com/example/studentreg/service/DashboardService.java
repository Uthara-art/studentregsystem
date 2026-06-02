package com.example.studentreg.service;

import java.util.Map;

public interface DashboardService {

    Map<String, Object> getDashboardStats();

    long getTotalRegistrations();

    int getAvailableSeats();

    // 🌟 FIX 1: Change return type from Object to long
    long getTotalStudents();

    // 🌟 FIX 2: Change return type from Object to long
    long getTotalCourses();
}