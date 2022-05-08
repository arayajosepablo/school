# School registration system

## Problem
Design and implement simple school registration system
- Assuming you already have a list of students
- Assuming you already have a list of courses
- A student can register to multiple courses
- A course can have multiple students enrolled in it.
- A course has 50 students maximum
- A student can register to 5 course maximum

Provide the following REST API:
- Create students CRUD
- Create courses CRUD
- Create API for students to register to courses
- Create abilities for user to view all relationships between students and courses
- Filter all students with a specific course
- Filter all courses for a specific student
- Filter all courses without any students
- Filter all students without any courses

## How to run it
Docker is required.

- First checkout the repository
- Execute `docker-compose up -d`. The first time this will take a few minutes.

## Endpoints
1. Students API
   - Get student by ID: GET `localhost:8080/v1/student/{studentID}`
   - Get all students: GET `localhost:8080/v1/student/all`
   - Create new student: POST `localhost:8080/v1/student/`
     - Payload:
     ```
     {
       "firstName": "Andrea",
       "lastName": "Doe",
       "email": "andread@gmail.com"
     }
     ```
   - Update student: PUT `localhost:8080/v1/student/{studentID}`
   - Delete student: DELETE `localhost:8080/v1/student/{studentID}`

2. Courses API
   - Get course by ID: GET `localhost:8080/v1/course/{courseID}`
   - Get all courses: GET `localhost:8080/v1/course/all`
   - Create new course: POST `localhost:8080/v1/course/`
     - Payload:
     ```
     {
       "courseName": "Engineering Introduction",
       "courseDescription": "Basics of Engineering"
     }
     ```
   - Update course: PUT `localhost:8080/v1/course/{courseID}`
   - Delete course: DELETE `localhost:8080/v1/course/{courseID}`

3. Register student in course
   - Register student in course: POST `localhost:8080/v1/student/{studentID}/register/{courseID}`

4. Filtering (reporting)
   - Filter all students with a specific course: GET `localhost:8080/v1/filter/course/{courseID}/students`
   - Filter all courses for a specific student: GET `localhost:8080/v1/filter/student/{studentID}/courses`
   - Filter all courses without any students: GET `localhost:8080/v1/filter/courses-without-students`
   - Filter all students without any courses: GET `localhost:8080/v1/filter/students-without-courses`

A Postman collection can be found in [Postman folder](postman).

## Technology stack:
- Java 11
- Maven
- Spring Boot
- Docker (docker-compose)
- JUnit
- MySQL
