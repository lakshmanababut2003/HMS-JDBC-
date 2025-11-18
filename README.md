Hospital Management System (HMS) – Java + JDBC

A layered, console-based Hospital Management System built using Core Java, JDBC, and PostgreSQL, following clean architecture principles (Controller → Service → Repository → Model/DTO).

This application manages Patients, Doctors, Appointments, Departments, Lab Tests, Prescriptions, Users, and Roles with authentication and role-based access.

📌 Features
User & Authentication

Login using username & password

Role-based access (Admin, Doctor, Lab Technician, Receptionist, Pharmacist)

Admin Module

Manage Departments

Manage Doctors

Manage Roles

Manage Users

Receptionist Module

Register/Manage Patients

Create & manage Appointments

Assign doctors & slots

Doctor Module

View assigned patients & appointments

Add diagnosis

Create prescriptions

Lab Technician Module

Manage Lab Tests

Upload Lab Reports

View pending tests

Pharmacist Module(Pending)

View prescriptions

Manage medicine dispensing

🧱 Architecture
src/
 ├── controllers/
 │     ├── AuthController
 │     ├── DoctorController
 │     ├── PatientController
 │     ├── ReceptionistController
 │     ├── LabTechnicianController
 │     ├── PharmacistController
 │     └── ManageMenus/*
 │
 ├── services/
 │     ├── DoctorService
 │     ├── PatientService
 │     ├── AppointmentService
 │     ├── LabService
 │     └── UserService
 │
 ├── repo/
 │     ├── DoctorRepo
 │     ├── PatientRepo
 │     ├── AppointmentRepo
 │     ├── LabReportRepo
 │     ├── DepartmentRepo
 │     └── UserRepo
 │
 ├── dto/
 │     ├── request/*
 │     └── response/*
 │
 ├── modals/
 │     ├── Doctor
 │     ├── Patient
 │     ├── Appointment
 │     └── LabReport
 │
 ├── dbConfig/
 │     └── DBConnection
 │
 └── App.java

🗄️ Database Requirements
Software

PostgreSQL 14+

Java 17+

Database Setup
CREATE DATABASE hospital_db;

-- create user if needed
-- CREATE USER postgres WITH PASSWORD 'your-password';

\c hospital_db

Tables

The system uses the following tables:

departments

doctors

patients

roles

users

appointments

lab_tests

lab_reports

prescriptions

slots

Your project includes SQL creation logic in the Repo InitLoad classes.

⚙️ Configuration

Update DB credentials in:

src/dbConfig/DBConnection.java

private static final String URL = "jdbc:postgresql://localhost:5432/hospital_db";
private static final String USER = "postgres";
private static final String PASSWORD = "your_password";

▶️ Running the Application
1. Compile

Linux/macOS:

javac -cp "lib/postgresql-42.7.8.jar" -d out $(find src -name "*.java")


Windows:

javac -cp "lib\postgresql-42.7.8.jar" -d out src\**\*.java

2. Run

Linux/macOS:

java -cp "out:lib/postgresql-42.7.8.jar" App


Windows:

java -cp "out;lib\postgresql-42.7.8.jar" App

🔑 Default Login (use your values)
Role	Username	Password
Admin	admin	admin123

(Other user creation handled through Admin > User Management)

📌 Core Design Principles Followed

Layered Architecture

DTO Pattern

Repository Pattern

Separation of Concerns

Input Validation

Error Handling

Expandable Menu-Based Flow

🧪 Future Improvements

Add Maven or Gradle build

Central logging framework (SLF4J)

Connection pooling (HikariCP)

Automated tests (JUnit)

Hash passwords (bcrypt)

Exception mapping + better messages

