package com.demo.schoolregistration.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student implements Serializable {

  private static final long serialVersionUID = -374848393833L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long studentId;

  private String studentName;
  private String mobileNumber;
  private String address;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "students", cascade = CascadeType.ALL)
  private Set<Course> courses;
}
