package com.metadata.school.service;

import com.metadata.school.model.Student;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StudentService {

  Student saveStudent(Student student);

  List<Student> getAllStudents();

  Student getById(Long studentId);

  Optional<Student> updateStudent(Student student);

  boolean deleteStudent(Long studentId);

  Optional<Student> registerStudentInCourse(Long studentId, Long courseId);

  Set<Student> getStudentsWithoutCourses();

}
