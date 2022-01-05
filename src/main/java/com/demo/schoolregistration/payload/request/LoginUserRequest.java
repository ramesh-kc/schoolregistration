package com.demo.schoolregistration.payload.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserRequest {

  @NotBlank
  private String username;

  @NotBlank
  private String password;
}
