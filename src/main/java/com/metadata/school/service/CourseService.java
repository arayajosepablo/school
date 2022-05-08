package com.metadata.school.service;

import com.metadata.school.model.Course;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CourseService {

  Course saveCourse(Course course);

  List<Course> getAllCourses();

  Course getById(Long courseId);

  Optional<Course> updateCourse(Course course);

  boolean deleteCourse(Long courseId);

  Set<Course> getCoursesWithoutStudents();

}
