package com.metadata.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "student")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "student_id", nullable = false, unique = true)
  private Long studentId;

  @NotNull
  @Column(name = "first_name", nullable = false)
  @Size(min = 2, max = 50, message = "The first name should have 2-50 characters")
  private String firstName;

  @NotNull
  @Column(name = "last_name", nullable = false)
  @Size(min = 2, max = 50, message = "The last name should have 2-50 characters")
  private String lastName;

  @NotNull
  @Column(name = "email", nullable = false)
  @Email(message = "Invalid email")
  @Size(min = 12, max = 70)
  private String email;

  @JsonIgnore
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "students_in_courses",
      joinColumns = @JoinColumn(name = "student_id"),
      inverseJoinColumns = @JoinColumn(name = "course_id"))
  private Set<Course> courses;
}
