package com.metadata.school.service.impl;

import com.metadata.school.model.Course;
import com.metadata.school.model.Student;
import com.metadata.school.service.CourseService;
import com.metadata.school.service.FilterService;
import com.metadata.school.service.StudentService;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class FilterServiceImpl implements FilterService {

  private final StudentService studentService;

  private final CourseService courseService;

  public FilterServiceImpl(final StudentService studentService, final CourseService courseService) {
    this.studentService = studentService;
    this.courseService = courseService;
  }

  @Override
  @Transactional
  public Optional<Set<Course>> getCoursesByStudent(final Long studentId) {
    final Student student = this.studentService.getById(studentId);
    if (student != null) {
      log.info("Courses by student {}", student.getCourses());
      return Optional.of(Collections.unmodifiableSet(student.getCourses()));
    }
    log.error("Student with ID {} not found", studentId);
    return Optional.empty();
  }

  @Override
  @Transactional
  public Optional<Set<Student>> getAllStudentsInCourse(final Long courseId) {
    final Course course = this.courseService.getById(courseId);
    if (course != null) {
      log.info("Students in course {}", course.getStudents());
      return Optional.of(Collections.unmodifiableSet(course.getStudents()));
    }
    log.error("Course with ID {} not found", courseId);
    return Optional.empty();
  }

  @Override
  @Transactional
  public Set<Course> getCoursesWithoutStudents() {
    Set<Course> result = this.courseService.getCoursesWithoutStudents();
    return Collections.unmodifiableSet(result);
  }

  @Override
  @Transactional
  public Set<Student> getStudentsWithoutCourses() {
    Set<Student> result = this.studentService.getStudentsWithoutCourses();
    return Collections.unmodifiableSet(result);
  }
}
