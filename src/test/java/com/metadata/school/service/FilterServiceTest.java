package com.metadata.school.service;

import com.metadata.school.model.Course;
import com.metadata.school.model.Student;
import com.metadata.school.service.impl.FilterServiceImpl;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class FilterServiceTest {

  @Mock
  private StudentService studentService;

  @Mock
  private CourseService courseService;

  @InjectMocks
  private FilterServiceImpl subject;

  private final static Long ID = 1L;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void getCoursesByStudent() {
    final Student student = StudentServiceTest.buildStudent();
    final Course course = CourseServiceTest.buildCourse();
    student.setCourses(Set.of(course));

    Mockito.when(this.studentService.getById(ID)).thenReturn(student);

    final Optional<Set<Course>> result = this.subject.getCoursesByStudent(ID);

    Mockito.verify(this.studentService).getById(student.getStudentId());
    Assertions.assertTrue(result.isPresent());
    Assertions.assertFalse(result.get().isEmpty());
    Assertions.assertEquals(1, result.get().size());
  }

  @Test
  public void getCoursesByStudentWhenNonExistingStudent() {
    Mockito.when(this.studentService.getById(ID)).thenReturn(null);

    final Optional<Set<Course>> result = this.subject.getCoursesByStudent(ID);

    Mockito.verify(this.studentService).getById(ID);
    Assertions.assertTrue(result.isEmpty());
  }

  @Test
  public void getCoursesByStudentWhenNotRegisteredCourses() {
    final Student student = StudentServiceTest.buildStudent();
    student.setCourses(Collections.emptySet());

    Mockito.when(this.studentService.getById(ID)).thenReturn(student);

    final Optional<Set<Course>> result = this.subject.getCoursesByStudent(ID);

    Mockito.verify(this.studentService).getById(student.getStudentId());
    Assertions.assertTrue(result.isPresent());
    Assertions.assertTrue(result.get().isEmpty());
  }

  @Test
  public void getAllStudentsInCourse() {
    final Student student = StudentServiceTest.buildStudent();
    final Course course = CourseServiceTest.buildCourse();
    course.setStudents(Set.of(student));

    Mockito.when(this.courseService.getById(ID)).thenReturn(course);

    final Optional<Set<Student>> result = this.subject.getAllStudentsInCourse(ID);

    Mockito.verify(this.courseService).getById(ID);
    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals(1, result.get().size());
  }

  @Test
  public void getAllStudentsInCourseWhenNonExistingCourse() {
    Mockito.when(this.courseService.getById(ID)).thenReturn(null);

    final Optional<Set<Student>> result = this.subject.getAllStudentsInCourse(ID);

    Mockito.verify(this.courseService).getById(ID);
    Assertions.assertTrue(result.isEmpty());
  }

  @Test
  public void getAllStudentsInCourseWhenNotRegisteredStudents() {
    final Course course = CourseServiceTest.buildCourse();
    course.setStudents(Collections.emptySet());

    Mockito.when(this.courseService.getById(ID)).thenReturn(course);

    final Optional<Set<Student>> result = this.subject.getAllStudentsInCourse(ID);

    Mockito.verify(this.courseService).getById(ID);
    Assertions.assertTrue(result.isPresent());
    Assertions.assertTrue(result.get().isEmpty());
  }

  @Test
  public void getCoursesWithoutStudents() {
    final Set<Course> courses = Set.of(CourseServiceTest.buildCourse());

    Mockito.when(this.courseService.getCoursesWithoutStudents()).thenReturn(courses);

    final Set<Course> result = this.subject.getCoursesWithoutStudents();

    Mockito.verify(this.courseService).getCoursesWithoutStudents();
    Assertions.assertFalse(result.isEmpty());
    Assertions.assertEquals(1, result.size());
  }

  @Test
  public void getStudentsWithoutCourses() {
    final Set<Student> students = Set.of(StudentServiceTest.buildStudent());

    Mockito.when(this.studentService.getStudentsWithoutCourses()).thenReturn(students);

    final Set<Student> result = this.subject.getStudentsWithoutCourses();

    Mockito.verify(this.studentService).getStudentsWithoutCourses();
    Assertions.assertFalse(result.isEmpty());
    Assertions.assertEquals(1, result.size());
  }

}
