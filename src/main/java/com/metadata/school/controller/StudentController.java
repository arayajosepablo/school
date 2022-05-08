package com.metadata.school.controller;

import com.metadata.school.model.Student;
import com.metadata.school.service.StudentService;
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
@RequestMapping("v1/student")
@Slf4j
public class StudentController {

  private final StudentService studentService;

  public StudentController(final StudentService studentService) {
    this.studentService = studentService;
  }

  @PostMapping
  public ResponseEntity<Student> saveStudent(@RequestBody @Valid final Student student) {
    try {
      return new ResponseEntity<>(this.studentService.saveStudent(student), HttpStatus.CREATED);
    } catch (DataIntegrityViolationException ex) {
      log.error("Bad request to register student. Error: {}. Payload {}", ex.getMessage(),
          student.toString());
      return ResponseEntity.badRequest().build();
    } catch (Exception e) {
      log.error("Error registering student. Error: {}", e.getMessage());
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping(path = "/all")
  public ResponseEntity<List<Student>> getAllStudents() {
    return ResponseEntity.ok(this.studentService.getAllStudents());
  }

  @GetMapping(path = "/{studentId}")
  public ResponseEntity<Student> getStudentById(@PathVariable final Long studentId) {
    final Student response = this.studentService.getById(studentId);
    if (response == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(response);
  }

  @PutMapping(path = "/{studentId}")
  public ResponseEntity<Student> updateStudent(@RequestBody @Valid final Student student,
      @PathVariable final Long studentId) {
    student.setStudentId(studentId);
    final Optional<Student> result = this.studentService.updateStudent(student);
    return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping(path = "/{studentId}")
  public ResponseEntity<Student> deleteStudent(@PathVariable final Long studentId) {
    if (this.studentService.deleteStudent(studentId)) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping(path = "{studentId}/register/{courseId}")
  public ResponseEntity<Object> registerStudentInCourse(@PathVariable final Long studentId,
      @PathVariable final Long courseId) {
    final Optional<Student> result = this.studentService.registerStudentInCourse(studentId,
        courseId);

    if (result.isPresent()) {
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }

}
