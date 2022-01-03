package com.demo.schoolregistration.service;

import com.demo.schoolregistration.model.Student;
import com.demo.schoolregistration.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentService {

  private final StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public Long addStudent(Student student) {
    student = studentRepository.save(student);
    log.info("Student {} successfully added", student.getStudentId());
    return student.getStudentId();
  }
}
