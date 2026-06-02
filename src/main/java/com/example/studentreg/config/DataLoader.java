package com.example.studentreg.config;

import com.example.studentreg.entity.Course;
import com.example.studentreg.entity.Student;
import com.example.studentreg.repository.CourseRepository;
import com.example.studentreg.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public DataLoader(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Load sample students if none exist
        if (studentRepository.count() == 0) {
            Student s1 = new Student("Alice", "alice@example.com", "password123", "CSE", 3);
            Student s2 = new Student("Bob", "bob@example.com", "password456", "ECE", 2);
            Student s3 = new Student("Charlie", "charlie@example.com", "password789", "IT", 4);

            studentRepository.save(s1);
            studentRepository.save(s2);
            studentRepository.save(s3);
        }

        // Load sample courses if none exist
        if (courseRepository.count() == 0) {
            Course c1 = new Course("Data Structures", "Learn about stacks, queues, and linked lists", "CS101", 50);
            Course c2 = new Course("Operating Systems", "Introduction to process management and memory", "CS102", 40);
            Course c3 = new Course("Database Systems", "Relational databases and SQL", "CS103", 45);

            courseRepository.save(c1);
            courseRepository.save(c2);
            courseRepository.save(c3);
        }

        System.out.println("✅ Sample Students and Courses Loaded Successfully!");
    }
}
