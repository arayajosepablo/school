package com.metadata.school.service;

import com.metadata.school.model.Course;
import com.metadata.school.model.Student;
import java.util.Optional;
import java.util.Set;

public interface FilterService {

  Optional<Set<Course>> getCoursesByStudent(Long studentId);

  Optional<Set<Student>> getAllStudentsInCourse(Long courseId);

  Set<Course> getCoursesWithoutStudents();

  Set<Student> getStudentsWithoutCourses();

}
