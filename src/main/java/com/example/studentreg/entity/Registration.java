package com.example.studentreg.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registrations")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // student and course are required (not-null)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = true)
    private String status; // e.g. "PENDING", "APPROVED", "REJECTED"

    @Column(name = "registered_at", nullable = true)
    private LocalDateTime registeredAt;

    public Registration() {
    }

    /**
     * Convenience constructor for creating a registration with student + course.
     * Avoid passing null values for required relationships.
     */
    public Registration(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.registeredAt = LocalDateTime.now();
        this.status = "APPROVED";
    }

    // full constructor if you need
    public Registration(Student student, Course course, String status, LocalDateTime registeredAt) {
        this.student = student;
        this.course = course;
        this.status = status;
        this.registeredAt = registeredAt;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    // no setId usually for generated ids, but included if needed
    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "id=" + id +
                ", student=" + (student != null ? student.getId() : "null") +
                ", course=" + (course != null ? course.getId() : "null") +
                ", status='" + status + '\'' +
                ", registeredAt=" + registeredAt +
                '}';
    }
}
