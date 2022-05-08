package com.metadata.school.controller;

import com.metadata.school.model.Course;
import com.metadata.school.model.Student;
import com.metadata.school.service.FilterService;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/filter")
@Slf4j
public class FilteringController {

  private final FilterService filterService;

  public FilteringController(final FilterService filterService) {
    this.filterService = filterService;
  }

  @GetMapping(path = "course/{courseId}/students")
  public ResponseEntity<Set<Student>> getStudentsInCourse(@PathVariable final Long courseId) {
    final Optional<Set<Student>> result = this.filterService.getAllStudentsInCourse(courseId);
    return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping(path = "student/{studentId}/courses")
  public ResponseEntity<Set<Course>> getCoursesByStudent(@PathVariable final Long studentId) {
    final Optional<Set<Course>> result =  this.filterService.getCoursesByStudent(studentId);
    return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping(path = "courses-without-students")
  public ResponseEntity<Set<Course>> getCoursesWithoutStudents() {
    return ResponseEntity.ok(this.filterService.getCoursesWithoutStudents());
  }

  @GetMapping(path = "students-without-courses")
  public ResponseEntity<Set<Student>> getStudentsWithoutCourses() {
    return ResponseEntity.ok(this.filterService.getStudentsWithoutCourses());
  }
}
