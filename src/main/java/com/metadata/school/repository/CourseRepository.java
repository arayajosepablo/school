package com.metadata.school.repository;

import com.metadata.school.model.Course;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

  @Query(value = "SELECT c.* "
      + "FROM course c "
      + "LEFT OUTER JOIN students_in_courses sic "
      + "ON c.course_id = sic.course_id "
      + "WHERE sic.course_id IS NULL", nativeQuery = true)
  Set<Course> getAllCoursesWithoutStudents();

  @Modifying
  @Query(value = "DELETE FROM students_in_courses "
      + "WHERE course_id = :courseId", nativeQuery = true)
  int deleteRelationship(@Param("courseId") Long courseId);
}
