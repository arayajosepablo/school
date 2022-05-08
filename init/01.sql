USE school-db;

CREATE TABLE IF NOT EXISTS course (
  course_id   int AUTO_INCREMENT NOT NULL,
  name        varchar(50) NOT NULL,
  description text,
  PRIMARY KEY (course_id)
);

CREATE TABLE IF NOT EXISTS student (
  student_id int AUTO_INCREMENT NOT NULL,
  first_name varchar(50) NOT NULL,
  last_name  varchar(50) NOT NULL,
  email      varchar(70) NOT NULL UNIQUE,
  PRIMARY KEY (student_id)
);

CREATE TABLE IF NOT EXISTS students_in_courses(
  student_id int NOT NULL,
  course_id  int NOT NULL,
  PRIMARY KEY (student_id, course_id),
  CONSTRAINT fk_student FOREIGN KEY(student_id) REFERENCES student(student_id),
  CONSTRAINT fk_course FOREIGN KEY(course_id) REFERENCES course(course_id)
);

INSERT INTO course(course_id, name, description) VALUES(1, 'Java', 'Java Fundamentals 1');
INSERT INTO course(course_id, name, description) VALUES(2, 'Git', 'Basic Git Commands');
INSERT INTO course(course_id, name, description) VALUES(3, 'NodeJS', 'NodeJS for Beginners');
INSERT INTO course(course_id, name, description) VALUES(4, 'Math 101', 'Math Fundamentals I');
INSERT INTO course(course_id, name, description) VALUES(5, 'Biology I', 'Introduction to Biology');

INSERT INTO student (student_id, first_name, last_name, email) VALUES(1, 'Helka', 'Smith', 'helkas@gmail.com');
INSERT INTO student (student_id, first_name, last_name, email) VALUES(2, 'Juan', 'Perez', 'juanpe@gmail.com');
INSERT INTO student (student_id, first_name, last_name, email) VALUES(3, 'Sam', 'Sagaz', 'ssz@gmail.com');

INSERT INTO students_in_courses (student_id, course_id) VALUES(1, 1);
INSERT INTO students_in_courses (student_id, course_id) VALUES(1, 2);
INSERT INTO students_in_courses (student_id, course_id) VALUES(1, 4);
INSERT INTO students_in_courses (student_id, course_id) VALUES(2, 3);
INSERT INTO students_in_courses (student_id, course_id) VALUES(2, 4);
