# Student Course Registration System (studentreg)

A modern, robust Spring Boot and Thymeleaf web application designed to facilitate course registrations for students and administrative operations for academic managers.

By default, the application runs out-of-the-box using an H2 in-memory database and populates dummy data for immediate testing. It can be easily configured to connect to MySQL for persistent storage.

---

## 🚀 Features

### 👨‍🎓 Student Role
* **Account Registration:** Create a new student profile with basic information, chosen branch, and current semester.
* **Custom Authentication:** Secure, credentials-based signup and login mechanisms.
* **Student Dashboard:** View personal profile details, currently registered courses, and real-time available courses.
* **Registration / Enrollment:** View available slots, browse the course catalog, and register for courses with real-time capacity checks.
* **Course Dropping:** Withdraw or drop registered courses instantly, updating enrollment stats dynamically.

### 👑 Administrator (Admin) Role
* **Secure Admin Access:** Access protected admin panels using Spring Security.
* **Metrics Dashboard:** View system-wide summaries including total registered students, course inventory count, and total enrollments.
* **Course Catalog Management:** Add new courses (specifying Course Code, Name, Description, and Max Capacity) or delete existing ones.
* **Student Directory:** Browse all registered students, view detailed profiles (including active registrations), and remove students from the system.
* **Supervised Enrollment:** View a centralized list of all active registrations, or register/drop students from courses directly on their behalf.

---

## 🛠️ Tech Stack

* **Backend Framework:** Spring Boot 3.5.6 (with Spring Web, Spring Security, and Spring Data JPA)
* **Database:** H2 Database (Default in-memory) or MySQL (Driver included)
* **Template Engine:** Thymeleaf with Spring Security integration
* **Frontend Styles:** Bootstrap 5.3.0 (Responsive CSS via CDN), Custom CSS (`auth.css`, `dashboard.css`)
* **Java Version:** Java 17
* **Build tool:** Maven 3.x

---

## 📁 Project Structure

```text
studentreg/
├── .mvn/                               # Maven Wrapper config
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── studentreg/
│   │   │               ├── StudentregApplication.java      # Main Entry Point
│   │   │               ├── config/
│   │   │               │   ├── DataLoader.java             # Database Seeder (Seeds Sample Data)
│   │   │               │   └── SecurityConfig.java         # Spring Security Configuration
│   │   │               ├── controller/
│   │   │               │   ├── AdminController.java        # Admin Portal Core Operations
│   │   │               │   ├── AdminCourseController.java  # Admin Catalog Management
│   │   │               │   ├── DiagnosticController.java   # App Diagnostics & Pings
│   │   │               │   ├── HomeController.java         # Homepage Navigation
│   │   │               │   ├── RegistrationController.java # Admin-led Course Assignments
│   │   │               │   ├── StudentAuthController.java  # Student Registration & Session Auth
│   │   │               │   ├── StudentCourseController.java# Student Course Browser
│   │   │               │   └── StudentDashboardController.java # Student Dashboard Operations
│   │   │               ├── entity/
│   │   │               │   ├── Course.java                 # Course Entity Class
│   │   │               │   ├── Registration.java           # Registration Link Class
│   │   │               │   ├── RegistrationStatus.java     # Registration State Enum (ENROLLED, DROPPED)
│   │   │               │   └── Student.java                # Student Entity Class
│   │   │               ├── repository/
│   │   │               │   ├── CourseRepository.java       # JPA Repository for Courses
│   │   │               │   ├── RegistrationRepository.java # JPA Repository for Registrations
│   │   │               │   └── StudentRepository.java      # JPA Repository for Students
│   │   │               └── service/
│   │   │                   ├── CourseService.java          # Service Interfaces
│   │   │                   ├── DashboardService.java
│   │   │                   ├── RegistrationService.java
│   │   │                   ├── StudentService.java
│   │   │                   └── impl/                       # Service Implementations (Business Logic)
│   │   │                       ├── CourseServiceImpl.java
│   │   │                       ├── DashboardServiceImpl.java
│   │   │                       ├── RegistrationServiceImpl.java
│   │   │                       └── StudentServiceImpl.java
│   │   └── resources/
│   │       ├── static/
│   │       │   └── css/
│   │       │       ├── auth.css                            # CSS styles for Auth/Login pages
│   │       │       └── dashboard.css                       # CSS styles for Dashboards
│   │       ├── templates/                                  # Thymeleaf HTML Templates
│   │       │   ├── admin-course-form.html
│   │       │   ├── admin-courses.html
│   │       │   ├── admin-dashboard.html
│   │       │   ├── admin-login.html
│   │       │   ├── admin-registrations.html
│   │       │   ├── admin-student-courses.html
│   │       │   ├── admin-student-detail.html
│   │       │   ├── admin-student-view.html
│   │       │   ├── admin-students.html
│   │       │   ├── home.html
│   │       │   ├── open-dashboard.html
│   │       │   ├── student-courses.html
│   │       │   ├── student-dashboard.html
│   │       │   ├── student-login.html
│   │       │   ├── student-register.html
│   │       │   └── student-signup.html
│   │       └── application.properties                      # Configuration settings
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── studentreg/
│                       └── StudentregApplicationTests.java # Context Load Test Suite
├── pom.xml                                                 # Maven Dependencies Configurations
├── mvnw                                                    # Maven wrapper script (Unix)
└── mvnw.cmd                                                # Maven wrapper script (Windows)
```

---

## 📋 Prerequisites

To run this project, make sure you have:
1. **Java SE Development Kit (JDK) 17** or higher installed.
2. **Maven 3.6+** (Optional, the wrapper script `./mvnw` or `mvnw.cmd` can be used directly).
3. **Web Browser** (Chrome, Firefox, Edge, Safari, etc.).

---

## ⚙️ Configuration & Installation Steps

### 1. Clone the Project
Navigate to your preferred directory and clone the codebase:
```bash
git clone <repository-url>
cd studentreg
```

### 2. Database Choice
By default, the application runs on a transient in-memory **H2 Database**. All tables are auto-generated on startup, and `DataLoader.java` seeds test data.

To use **MySQL** instead:
1. Open `src/main/resources/application.properties`.
2. Append the datasource properties:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/studentreg_db?useSSL=false&serverTimezone=UTC
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```
3. Make sure to create a database named `studentreg_db` in MySQL server prior to starting the application.

---

## 🏃 How to Run Using Maven

You can compile and start the web server using Maven tools.

### Compile Project
Verify the code is building correctly:
* **Windows (Command Prompt/PowerShell):**
  ```cmd
  mvnw.cmd clean compile
  ```
* **macOS / Linux:**
  ```bash
  chmod +x mvnw
  ./mvnw clean compile
  ```

### Run Application
Start the Spring Boot application:
* **Windows (PowerShell):**
  ```powershell
  .\mvnw spring-boot:run
  ```
* **macOS / Linux:**
  ```bash
  ./mvnw spring-boot:run
  ```

Once compilation finishes and the application console states that Tomcat has started, launch your browser and navigate to:
```text
http://localhost:8080/
```

---

## 🔑 Login Credentials

The application initializes with default users populated by `DataLoader.java` and `SecurityConfig.java`:

| Role | Username / Email | Password | Account Context |
| :--- | :--- | :--- | :--- |
| **Administrator** | `admin` | `adminpass` | Global Admin operations |
| **Student** | `alice@example.com` | `password123` | DB Registered Student (Alice) |
| **Student** | `bob@example.com` | `password456` | DB Registered Student (Bob) |
| **Student** | `charlie@example.com` | `password789` | DB Registered Student (Charlie) |

---

## 🔗 Endpoints Reference

### Public / Root Endpoints
| HTTP Method | Path | Description |
| :--- | :--- | :--- |
| `GET` | `/` | Web Application homepage with portal routing |
| `GET` | `/__ping` | Server health diagnostics check |
| `GET` | `/open-dashboard` | Open-access dashboard mockup |

### Student Endpoints
| HTTP Method | Path | Query / Path Param | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/student/signup` | None | Displays Student Register/Sign-up page |
| `POST` | `/student/signup` | Model attribute `student` | Handles Student account registration |
| `GET` | `/student/login` | None | Displays Student Login credentials form |
| `POST` | `/student/login` | Form parameters `email`, `password` | Processes student authentication and starts session |
| `GET` | `/student/dashboard` | `studentId` (Query) | Main student portal (Personal profile & enrollments) |
| `GET` | `/student/courses` | `studentId` (Query) | Student-viewable course catalog browser |
| `POST` | `/student/course/register` | `studentId`, `courseId` (Form) | Register student to a course |
| `POST` | `/student/course/drop` | `studentId`, `courseId` (Form) | Drop a registered course |

### Administrative (Admin) Endpoints
*Note: These endpoints require role authentication (`ADMIN`) through Spring Security form login.*

| HTTP Method | Path | Form / Path Param | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/admin/login` | None | Admin Spring Security form login page |
| `GET` | `/admin/dashboard` | None | Admin dashboard displaying platform statistics |
| `GET` | `/admin/courses` | None | List courses and includes Course addition form |
| `POST` | `/admin/courses/add` | Model attribute `course` | Save a new course to inventory |
| `POST` | `/admin/courses/delete/{id}` | `{id}` (Path) | Delete course from catalog |
| `GET` | `/admin/students` | None | Lists all students registered in system |
| `GET` | `/admin/students/view/{id}` | `{id}` (Path) | View details and active registrations for a student |
| `POST` | `/admin/students/delete/{id}` | `{id}` (Path) | Deletes a student account |
| `GET` | `/admin/registrations` | None | View all system course registration logs |
| `GET` | `/admin/students/{studentId}/courses`| `{studentId}` (Path) | Manage registrations of a student |
| `POST`| `/admin/students/{studentId}/courses/register` | `{studentId}`, `courseId` | Direct admin-assigned course enrollment |
| `POST`| `/admin/students/{studentId}/courses/drop` | `{studentId}`, `courseId` | Direct admin-assigned course drop |

---

## 🔮 Future Improvements

1. **Secure Session Management:** Transition the custom student login routing from plain HTTP query strings (`studentId`) to Spring Security-backed authentication context or secure HTTP Session cookies.
2. **Student Password Hashing:** Apply cryptographic encoding (e.g., `BCryptPasswordEncoder`) for student accounts during signup; currently, student credentials are saved in plain text.
3. **Form Request Validation:** Integrate Java Bean Validation (using `@Valid` and constraints like `@NotBlank`, `@Email`, `@Size`) on inputs inside controllers.
4. **Interactive Course Catalog Editing:** Implement standard updating mechanisms (GET/POST edits) to modify existing courses instead of only creating/deleting.
5. **Pagination & Query Search:** Add search filters and paginate rosters for students, courses, and registrations, optimizing DB performance for larger student enrollments.
6. **Robust Error Handling:** Design user-friendly error views for exceptions like `IllegalStateException` rather than standard whitelabel error pages.
