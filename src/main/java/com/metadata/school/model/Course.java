package com.metadata.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@Entity
@Table(name = "course")
@Slf4j
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "course_id", nullable = false, unique = true)
  private Long courseId;

  @NotNull
  @Column(name = "name", nullable = false)
  @Size(min = 3, max = 50, message = "Course name should have 3-50 characters")
  private String courseName;

  @Column(name = "description")
  private String courseDescription;

  @JsonIgnore
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
  private Set<Student> students;

}
