package com.metadata.school.service;

import com.metadata.school.model.Course;
import com.metadata.school.model.Student;
import com.metadata.school.repository.StudentRepository;
import com.metadata.school.service.impl.StudentServiceImpl;
import java.util.Collections;
import java.util.HashSet;
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

public class StudentServiceTest {

  @Mock
  private StudentRepository studentRepository;

  @Mock
  private CourseService courseService;

  @InjectMocks
  private StudentServiceImpl subject;

  private static final Long ID = 1L;

  private static final String NAME = "Helka";

  private static final String LAST_NAME = "Smith";

  private static final String EMAIL = "helkas@student.com";

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void saveStudent() {
    final Student student = new Student();
    student.setFirstName(NAME);
    student.setLastName(LAST_NAME);
    student.setEmail(EMAIL);

    Mockito.when(this.studentRepository.save(student)).thenReturn(buildStudent());

    this.subject.saveStudent(student);

    Mockito.verify(this.studentRepository).save(student);
  }

  @Test
  public void getAllStudents() {
    Mockito.when(this.studentRepository.findAll()).thenReturn(Collections.emptyList());

    this.subject.getAllStudents();

    Mockito.verify(this.studentRepository).findAll();
  }

  @Test
  public void getByIdWhenExisting() {
    Mockito.when(this.studentRepository.findById(ID)).thenReturn(Optional.of(buildStudent()));

    final Student result = this.subject.getById(ID);

    Mockito.verify(this.studentRepository).findById(ID);
    Assertions.assertEquals(ID, result.getStudentId());
  }

  @Test
  public void getByIdWhenNotExisting() {
    Mockito.when(this.studentRepository.findById(ID)).thenReturn(Optional.empty());

    final Student result = this.subject.getById(ID);

    Mockito.verify(this.studentRepository).findById(ID);
    Assertions.assertNull(result);
  }

  @Test
  public void updateStudentWhenExisting() {
    final String email = "newemail@domain.com";
    final Student toUpdate = buildStudent();
    final Student expectedResult = buildStudent();
    final Student student = buildStudent();
    expectedResult.setEmail(email);
    student.setEmail(email);

    Mockito.when(this.studentRepository.findById(ID)).thenReturn(Optional.of(toUpdate));
    Mockito.when(this.studentRepository.save(student)).thenReturn(expectedResult);

    final Student result = this.subject.updateStudent(student).get();

    Mockito.verify(this.studentRepository).findById(student.getStudentId());
    Mockito.verify(this.studentRepository).save(student);
    Assertions.assertEquals(expectedResult.getStudentId(), result.getStudentId());
    Assertions.assertEquals(expectedResult.getFirstName(), result.getFirstName());
    Assertions.assertEquals(expectedResult.getLastName(), result.getLastName());
    Assertions.assertEquals(expectedResult.getEmail(), result.getEmail());
  }

  @Test
  public void updateStudentWhenNotExisting() {
    Mockito.when(this.studentRepository.findById(ID)).thenReturn(Optional.empty());

    Assertions.assertTrue(this.subject.updateStudent(buildStudent()).isEmpty());
    Mockito.verify(this.studentRepository).findById(ID);
  }

  @Test
  public void deleteCourse() {
    Mockito.doNothing().when(this.studentRepository).deleteById(ID);

    final boolean result = this.subject.deleteStudent(ID);

    Mockito.verify(this.studentRepository).deleteById(ID);
    Assertions.assertTrue(result);
  }

  @Test
  public void deleteCourseWhenException() {
    Mockito.doNothing().when(this.studentRepository).deleteById(ID);
    Mockito.doThrow(new EmptyResultDataAccessException(5)).when(this.studentRepository)
        .deleteById(ID);

    final boolean result = this.subject.deleteStudent(ID);

    Mockito.verify(this.studentRepository).deleteById(ID);
    Assertions.assertFalse(result);
  }

  @Test
  public void registerStudentInCourse() {
    final Course c = new Course();
    c.setCourseId(ID);
    c.setCourseDescription("Description");
    c.setCourseName("Name");
    c.setStudents(new HashSet<>());

    final Student s = buildStudent();
    s.setCourses(new HashSet<>());

    final Student registered = buildStudent();
    registered.setCourses(Set.of(c));

    Mockito.when(this.courseService.getById(ID)).thenReturn(c);
    Mockito.when(this.studentRepository.findById(ID)).thenReturn(Optional.of(s));
    Mockito.when(this.studentRepository.save(registered)).thenReturn(registered);

    final Student result = this.subject.registerStudentInCourse(ID, ID).get();

    Mockito.verify(this.courseService).getById(ID);
    Mockito.verify(this.studentRepository).findById(ID);
    Mockito.verify(this.studentRepository).save(registered);

    Assertions.assertFalse(result.getCourses().isEmpty());
  }

  @Test
  public void registerStudentInCourseWhenNonExistingCourse() {
    Mockito.when(this.courseService.getById(ID)).thenReturn(null);

    final Optional<Student> result = this.subject.registerStudentInCourse(ID, ID);

    Mockito.verify(this.courseService).getById(ID);
    Assertions.assertTrue(result.isEmpty());
  }

  @Test
  public void registerStudentInCourseWhenNonExistingStudent() {
    final Course c = new Course();
    c.setCourseId(ID);
    c.setCourseDescription("Description");
    c.setCourseName("Name");
    c.setStudents(new HashSet<>());

    Mockito.when(this.courseService.getById(ID)).thenReturn(c);
    Mockito.when(this.studentRepository.findById(ID)).thenReturn(Optional.empty());

    final Optional<Student> result = this.subject.registerStudentInCourse(ID, ID);

    Mockito.verify(this.courseService).getById(ID);
    Mockito.verify(this.studentRepository).findById(ID);

    Assertions.assertTrue(result.isEmpty());
  }

  @Test
  public void getStudentsWithoutCourses() {
    final Set<Student> expectedResult = Set.of(buildStudent());
    Mockito.when(this.studentRepository.getAllStudentsWithoutCourses()).thenReturn(expectedResult);
    final Set<Student> result = this.subject.getStudentsWithoutCourses();

    Mockito.verify(this.studentRepository).getAllStudentsWithoutCourses();
    Assertions.assertFalse(result.isEmpty());
  }

  @Test
  public void getStudentsWithoutCoursesWhenEmptyResult() {
    Mockito.when(this.studentRepository.getAllStudentsWithoutCourses())
        .thenReturn(Collections.emptySet());
    final Set<Student> result = this.subject.getStudentsWithoutCourses();

    Mockito.verify(this.studentRepository).getAllStudentsWithoutCourses();
    Assertions.assertTrue(result.isEmpty());
  }

  public static Student buildStudent() {
    final Student s = new Student();
    s.setStudentId(ID);
    s.setFirstName(NAME);
    s.setLastName(LAST_NAME);
    s.setEmail(EMAIL);

    return s;
  }

}
