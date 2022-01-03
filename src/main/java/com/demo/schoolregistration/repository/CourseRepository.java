package com.demo.schoolregistration.repository;

import com.demo.schoolregistration.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {}
