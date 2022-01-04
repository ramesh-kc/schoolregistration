package com.demo.schoolregistration.controller;

import com.demo.schoolregistration.model.Course;
import com.demo.schoolregistration.model.Student;
import com.demo.schoolregistration.service.CourseService;
import com.demo.schoolregistration.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class StudentRegistrationController {

  private final StudentService studentService;
  private final CourseService courseService;

  public StudentRegistrationController(StudentService studentService, CourseService courseService) {
    this.studentService = studentService;
    this.courseService = courseService;
  }

  @PostMapping("/students")
  public Long addStudent(@RequestBody Student student) {
    Long studentId = studentService.addStudent(student);
    log.info("Student with name : " + student.getStudentName() + " has been added successfully.");
    return studentId;
  }

  @PostMapping("/courses")
  public Long addCourses(@RequestBody Course course) {
    Long courseId = courseService.addCourse(course);
    log.info("Course with name " + course.getCourseName() + " has been added successfully.");
    return courseId;
  }
}
