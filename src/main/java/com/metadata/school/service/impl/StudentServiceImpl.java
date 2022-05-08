package com.metadata.school.service.impl;

import com.metadata.school.model.Course;
import com.metadata.school.model.Student;
import com.metadata.school.repository.StudentRepository;
import com.metadata.school.service.CourseService;
import com.metadata.school.service.StudentService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;

  private final CourseService courseService;

  public StudentServiceImpl(final StudentRepository studentRepository,
      final CourseService courseService) {
    this.studentRepository = studentRepository;
    this.courseService = courseService;
  }

  @Override
  @Transactional
  public Student saveStudent(final Student student) {
    return this.studentRepository.save(student);
  }

  @Override
  @Transactional
  public List<Student> getAllStudents() {
    return this.studentRepository.findAll();
  }

  @Override
  @Transactional
  public Student getById(final Long studentId) {
    final Optional<Student> student = this.studentRepository.findById(studentId);
    return student.orElse(null);
  }

  @Override
  @Transactional
  public Optional<Student> updateStudent(final Student student) {
    if (this.getById(student.getStudentId()) != null) {
      return Optional.of(this.saveStudent(student));
    }
    log.error("Student with ID {} not found", student.getStudentId());
    return Optional.empty();
  }

  @Override
  @Transactional
  public boolean deleteStudent(final Long studentId) {
    try {
      this.studentRepository.deleteRelationship(studentId);
      this.studentRepository.deleteById(studentId);
      return true;
    } catch (EmptyResultDataAccessException e) {
      log.error("Student with ID {} not found", studentId);
      return false;
    }
  }

  @Override
  @Transactional
  public Optional<Student> registerStudentInCourse(final Long studentId, final Long courseId) {
    final Course course = this.courseService.getById(courseId);
    if (course != null) {
      final Student student = this.getById(studentId);
      if (student != null) {
        if (isLimitNotReach(student, course)) {
          student.getCourses().add(course);
          return Optional.of(this.studentRepository.save(student));
        }
      } else {
        log.error("Student with ID {} not found", studentId);
      }
    } else {
      log.error("Course with ID {} not found", courseId);
    }
    return Optional.empty();
  }

  @Override
  public Set<Student> getStudentsWithoutCourses() {
    Set<Student> result = this.studentRepository.getAllStudentsWithoutCourses();
    return Collections.unmodifiableSet(result);
  }

  private boolean isLimitNotReach(final Student student, final Course course) {
    return student.getCourses().size() < 5 && course.getStudents().size() < 50;
  }

}
