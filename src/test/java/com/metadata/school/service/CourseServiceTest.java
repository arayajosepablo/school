package com.metadata.school.service;

import com.metadata.school.model.Course;
import com.metadata.school.repository.CourseRepository;
import com.metadata.school.service.impl.CourseServiceImpl;
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
import org.springframework.dao.EmptyResultDataAccessException;

public class CourseServiceTest {

  @Mock
  private CourseRepository courseRepository;

  @InjectMocks
  private CourseServiceImpl subject;

  private static final Long ID = 1L;

  private static final String COURSE_NAME = "Java";

  private static final String COURSE_DESCRIPTION = "Java fundamentals I";

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void saveCourse() {
    final Course course = new Course();
    course.setCourseName(COURSE_NAME);
    course.setCourseDescription(COURSE_DESCRIPTION);

    Mockito.when(this.courseRepository.save(course)).thenReturn(buildCourse());

    this.subject.saveCourse(course);

    Mockito.verify(this.courseRepository).save(course);
  }

  @Test
  public void getAllCourses() {
    Mockito.when(this.courseRepository.findAll()).thenReturn(Collections.emptyList());

    this.subject.getAllCourses();

    Mockito.verify(this.courseRepository).findAll();
  }

  @Test
  public void getByIdWhenExisting() {
    Mockito.when(this.courseRepository.findById(ID)).thenReturn(Optional.of(buildCourse()));

    final Course result = this.subject.getById(ID);

    Mockito.verify(this.courseRepository).findById(ID);
    Assertions.assertEquals(ID, result.getCourseId());
  }

  @Test
  public void getByIdWhenNotExisting() {
    Mockito.when(this.courseRepository.findById(ID)).thenReturn(Optional.empty());

    final Course result = this.subject.getById(ID);

    Mockito.verify(this.courseRepository).findById(ID);
    Assertions.assertNull(result);
  }

  @Test
  public void updateCourseWhenExisting() {
    final String courseDescription = "Advanced Java";
    final Course courseToUpdate = buildCourse();
    final Course expectedResult = buildCourse();
    final Course course = buildCourse();
    expectedResult.setCourseDescription(courseDescription);
    course.setCourseDescription(courseDescription);

    Mockito.when(this.courseRepository.findById(ID)).thenReturn(Optional.of(courseToUpdate));
    Mockito.when(this.courseRepository.save(course)).thenReturn(expectedResult);

    final Course result = this.subject.updateCourse(course).get();

    Mockito.verify(this.courseRepository).findById(course.getCourseId());
    Mockito.verify(this.courseRepository).save(course);
    Assertions.assertEquals(expectedResult.getCourseId(), result.getCourseId());
    Assertions.assertEquals(expectedResult.getCourseDescription(), result.getCourseDescription());
    Assertions.assertEquals(expectedResult.getCourseName(), result.getCourseName());
  }

  @Test
  public void updateCourseWhenNotExisting() {
    Mockito.when(this.courseRepository.findById(ID)).thenReturn(Optional.empty());

    Assertions.assertTrue(this.subject.updateCourse(buildCourse()).isEmpty());
    Mockito.verify(this.courseRepository).findById(ID);
  }

  @Test
  public void deleteCourse() {
    Mockito.doNothing().when(this.courseRepository).deleteById(ID);

    final boolean result = this.subject.deleteCourse(ID);

    Mockito.verify(this.courseRepository).deleteById(ID);
    Assertions.assertTrue(result);
  }

  @Test
  public void deleteCourseWhenException() {
    Mockito.doNothing().when(this.courseRepository).deleteById(ID);
    Mockito.doThrow(new EmptyResultDataAccessException(5)).when(this.courseRepository)
        .deleteById(ID);

    final boolean result = this.subject.deleteCourse(ID);

    Mockito.verify(this.courseRepository).deleteById(ID);
    Assertions.assertFalse(result);
  }

  @Test
  public void getCoursesWithoutStudents() {
    final Set<Course> expectedResult = Set.of(buildCourse());
    Mockito.when(this.courseRepository.getAllCoursesWithoutStudents()).thenReturn(expectedResult);
    final Set<Course> result = this.subject.getCoursesWithoutStudents();

    Mockito.verify(this.courseRepository).getAllCoursesWithoutStudents();
    Assertions.assertFalse(result.isEmpty());
  }

  @Test
  public void getCoursesWithoutStudentsWhenEmptyResult() {
    Mockito.when(this.courseRepository.getAllCoursesWithoutStudents())
        .thenReturn(Collections.emptySet());
    final Set<Course> result = this.subject.getCoursesWithoutStudents();

    Mockito.verify(this.courseRepository).getAllCoursesWithoutStudents();
    Assertions.assertTrue(result.isEmpty());
  }

  public static Course buildCourse() {
    final Course course = new Course();
    course.setCourseId(ID);
    course.setCourseName(COURSE_NAME);
    course.setCourseDescription(COURSE_DESCRIPTION);

    return course;
  }

}
