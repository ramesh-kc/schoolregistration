package com.demo.schoolregistration.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Course implements Serializable {
  private static final long serialVersionUID = -374848383733333L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long courseId;

  private String courseName;
  private String courseDescription;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(
      name = "student_course",
      joinColumns = {@JoinColumn(name = "course_id", nullable = false, updatable = false)},
      inverseJoinColumns = {@JoinColumn(name = "student_id", nullable = false, updatable = false)})
  private Set<Student> students;
}
