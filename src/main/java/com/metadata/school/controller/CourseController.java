package com.metadata.school.controller;

import com.metadata.school.model.Course;
import com.metadata.school.service.CourseService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/course")
@Slf4j
public class CourseController {

  private final CourseService courseService;

  public CourseController(final CourseService courseService) {
    this.courseService = courseService;
  }

  @PostMapping
  public ResponseEntity<Course> saveCourse(@RequestBody @Valid final Course course) {
    try {
      return new ResponseEntity<>(this.courseService.saveCourse(course), HttpStatus.CREATED);
    } catch (DataIntegrityViolationException ex) {
      log.error("Bad request to register course. Error: {}. Payload {}", ex.getMessage(),
          course.toString());
      return ResponseEntity.badRequest().build();
    } catch (Exception e) {
      log.error("Error registering course. Error: {}", e.getMessage());
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping(path = "/all")
  public ResponseEntity<List<Course>> getAllCourses() {
    return ResponseEntity.ok(this.courseService.getAllCourses());
  }

  @GetMapping(path = "/{courseId}")
  public ResponseEntity<Course> getCourseById(@PathVariable final Long courseId) {
    final Course response = this.courseService.getById(courseId);
    if (response == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(response);
  }

  @PutMapping(path = "/{courseId}")
  public ResponseEntity<Course> updateCourse(@RequestBody @Valid final Course course,
      @PathVariable final Long courseId) {
    course.setCourseId(courseId);
    final Optional<Course> result = this.courseService.updateCourse(course);
    return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping(path = "/{courseId}")
  public ResponseEntity<Course> deleteCourse(@PathVariable final Long courseId) {
    if (this.courseService.deleteCourse(courseId)) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

}
