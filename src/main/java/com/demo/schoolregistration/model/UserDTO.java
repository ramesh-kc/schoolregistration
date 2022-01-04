package com.demo.schoolregistration.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
  private String username;
  private String password;

  private String email;
  private String phone;
  private String name;
  private String designation;

}
