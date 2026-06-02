package com.example.studentreg.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) // Added constraints for course code
    private String code; // 🌟 ADDED: Field for the course code

    private String name;
    private String description;
    private int capacity; // total seats
    private int enrolled; // how many students have enrolled

    public Course() {
    }

    public Course(String name, String description, String code, int capacity) {
        this.name = name;
        this.description = description;
        this.code = code; // 🌟 UPDATED: Store the code in the new field
        this.capacity = capacity;
        this.enrolled = 0;
    }

    // Getters and Setters

    // 🌟 ADDED: Getter for the code field to resolve Thymeleaf error (EL1008E)
    public String getCode() {
        return code;
    }

    // 🌟 ADDED: Setter for the code field
    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    // Setter for ID is usually omitted for generated IDs, but including for
    // completeness if needed
    /*
     * public void setId(Long id) {
     * this.id = id;
     * }
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(int enrolled) {
        this.enrolled = enrolled;
    }

    public int getAvailableSeats() {
        return capacity - enrolled;
    }
}