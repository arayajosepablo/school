package com.metadata.school.repository;

import com.metadata.school.model.Student;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

  @Query(value = "SELECT s.* "
      + "FROM student s "
      + "LEFT OUTER JOIN students_in_courses sic "
      + "ON s.student_id = sic.student_id "
      + "WHERE sic.student_id IS NULL", nativeQuery = true)
  Set<Student> getAllStudentsWithoutCourses();

  @Modifying
  @Query(value = "DELETE FROM students_in_courses "
      + "WHERE student_id = :studentId", nativeQuery = true)
  int deleteRelationship(@Param("studentId") Long studentId);
}
