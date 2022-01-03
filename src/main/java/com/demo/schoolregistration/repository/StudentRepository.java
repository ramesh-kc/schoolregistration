package com.demo.schoolregistration.repository;

import com.demo.schoolregistration.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {}
