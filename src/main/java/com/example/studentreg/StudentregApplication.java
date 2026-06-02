package com.example.studentreg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application entry. We explicitly set the component scan base package
 * so Spring will find controllers placed under com.example.studentreg.*.
 */
@SpringBootApplication(scanBasePackages = "com.example.studentreg")
public class StudentregApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentregApplication.class, args);
	}
}
