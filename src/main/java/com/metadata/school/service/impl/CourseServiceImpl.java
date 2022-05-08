package com.metadata.school.service.impl;

import com.metadata.school.model.Course;
import com.metadata.school.repository.CourseRepository;
import com.metadata.school.service.CourseService;
import com.metadata.school.service.StudentService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courseRepository;

  public CourseServiceImpl(final CourseRepository courseRepository,
      @Lazy final StudentService studentService) {
    this.courseRepository = courseRepository;
  }

  @Override
  @Transactional
  public Course saveCourse(final Course course) {
    return this.courseRepository.save(course);
  }

  @Override
  @Transactional
  public List<Course> getAllCourses() {
    return this.courseRepository.findAll();
  }

  @Override
  @Transactional
  public Course getById(final Long courseId) {
    final Optional<Course> c = this.courseRepository.findById(courseId);

    return c.orElse(null);
  }

  @Override
  @Transactional
  public Optional<Course> updateCourse(final Course course) {
    if (this.getById(course.getCourseId()) != null) {
      return Optional.of(this.saveCourse(course));
    }
    log.error("Course with ID {} not found", course.getCourseId());
    return Optional.empty();
  }

  @Override
  @Transactional
  public boolean deleteCourse(final Long courseId) {
    try {
      this.courseRepository.deleteRelationship(courseId);
      this.courseRepository.deleteById(courseId);
      return true;
    } catch (EmptyResultDataAccessException e) {
      log.error("Course with ID {} not found", courseId);
      return false;
    }
  }

  @Override
  @Transactional
  public Set<Course> getCoursesWithoutStudents() {
    Set<Course> result = this.courseRepository.getAllCoursesWithoutStudents();
    return Collections.unmodifiableSet(result);
  }
}
