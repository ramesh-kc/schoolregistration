package com.demo.schoolregistration.service;

import com.demo.schoolregistration.model.Course;
import com.demo.schoolregistration.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CourseService {

  private final CourseRepository courseRepository;

  public CourseService(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  public Long addCourse(Course course) {
    course = courseRepository.save(course);
    log.info("Course {} has been successfully added : " , course.getCourseId());
    return course.getCourseId();
  }
}
